import com.example.demo.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthCounterController {

    private final UserService userService;

    public AuthCounterController(UserService userService) {
        this.userService = userService;
    }
}
