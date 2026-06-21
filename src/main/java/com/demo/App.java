@RestController
public class AppController {

    @GetMapping("/")
    public String home() {
        return "Jenkins + Docker + ArgoCD + EKS Success!";
    }
}
