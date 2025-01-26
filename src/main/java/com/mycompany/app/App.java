package com.mycompany.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@Controller
public class App {
    private int deployCount = 0;
    private String lastDeployment = "-";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Value("${app.jenkins.url}")
    private String jenkinsUrl;
    
    @Value("${app.nginx.url}")
    private String nginxUrl;
    
    @Value("${app.grafana.url}")
    private String grafanaUrl;
    
    @Value("${app.prometheus.url}")
    private String prometheusUrl;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("currentTime", LocalDateTime.now().format(formatter));
        model.addAttribute("deployCount", deployCount);
        model.addAttribute("lastDeployment", lastDeployment);
        model.addAttribute("environment", "Development");
        model.addAttribute("status", "Active");
        model.addAttribute("jenkinsUrl", jenkinsUrl);
        model.addAttribute("nginxUrl", nginxUrl);
        model.addAttribute("grafanaUrl", grafanaUrl);
        model.addAttribute("prometheusUrl", prometheusUrl);
        return "dashboard";
    }

    @GetMapping("/deploy")
    public String simulateDeploy() {
        deployCount++;
        lastDeployment = LocalDateTime.now().format(formatter);
        return "redirect:/";
    }
}
