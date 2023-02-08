package main;

import main.dto.DtoMessage;
import main.dto.MessageMapper;
import main.model.Message;
import main.model.User;
import main.repository.MessageRepository;
import main.repository.UserRepository;
import net.bytebuddy.TypeCache;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ChatController {

    @Autowired
    private UserRepository userRepository;

    private MessageMapper messageMapper;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/init")
    public HashMap<String, Boolean> init() {
        HashMap<String, Boolean> response = new HashMap<>();

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        Optional<User> userOpt = userRepository.findBySessionId(sessionId);

        response.put("result", userOpt.isPresent());
        return response;
    }

    @PostMapping("/auth")
    public HashMap<String, Boolean> auth(@RequestParam String name) {
        HashMap<String, Boolean> response = new HashMap<>();

        User user = new User();
        user.setName(name);
        user.setSessionId(Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getSessionId());

        userRepository.save(user);

        response.put("result", true);
        return response;
    }

    @PostMapping("/message")
    public HashMap<String, Boolean> sendMessage(@RequestParam String message) {
        HashMap<String, Boolean> response = new HashMap<>();

        if (Strings.isEmpty(message)) {
            response.put("result", false);
            return response;
        }

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        User user = userRepository.findBySessionId(sessionId).get();
        Message msg = new Message();
        msg.setDateTime(LocalDateTime.now());
        msg.setMessage(message);
        msg.setUser(user);
        messageRepository.save(msg);

        response.put("result", true);

        return response;
    }

    @GetMapping("/message")
    public List<DtoMessage> getMessagesList() {
        return messageRepository
                .findAll(Sort.by(Sort.Direction.ASC, "dateTime"))
                .stream()
                .map(MessageMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("/user")
    public HashMap<Integer, String> getUsersList() {
        return null;
    }
}
