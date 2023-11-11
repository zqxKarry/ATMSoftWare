package t01.six.atm_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import t01.six.atm_backend.service.impl.AdminServiceImpl;
import t01.six.atm_backend.utils.Result;

@RestController
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    AdminServiceImpl adminService;
    
    @GetMapping("/check-id")
    public Result<?> AdminCheckId(@RequestParam(defaultValue = "") String adminId,
                                @RequestParam(defaultValue = "") String atmId)
    {
        return adminService.checkAdminId(adminId,atmId);
    }

    @GetMapping("/check-pass")
    public Result<?> AdminCheckPass(@RequestParam(defaultValue = "") String adminId,
                                    @RequestParam(defaultValue = "") String atmId,
                                    @RequestParam(defaultValue = "") String adminPass)
    {
        return adminService.checkAdminPass(adminId,atmId,adminPass);
    }
    
}
