package t01.six.atm_backend.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import t01.six.atm_backend.service.impl.AtmMachineServiceImpl;
import t01.six.atm_backend.utils.Result;

@RestController
@RequestMapping("/atm")
public class AtmMachineController {
    @Autowired
    AtmMachineServiceImpl atmMachineService;

    @GetMapping("/check-bala")
    public Result<?> checkAtmBalance(@RequestParam(defaultValue = "") String atmId)
    {
        return atmMachineService.checkAtmBalance(atmId);
    }

    @PostMapping("/admin-addrmb")
    public Result<?> adminAddRMB(@RequestParam Integer addCount,
                                @RequestParam(defaultValue = "") String atmId)
    {
        return atmMachineService.adminAddRMB(addCount, atmId);
    }

    @PostMapping("/admin-takermb")
    public Result<?> adminTakeRMB(@RequestParam Integer takeCount,
                                @RequestParam(defaultValue = "") String atmId)
    {
        return atmMachineService.adminTakeRMB(takeCount, atmId);
    }

    @PostMapping("/admin-addpaper")
    public Result<?> adminAddPaper(@RequestParam Integer addPaperCount,
                                @RequestParam(defaultValue = "") String atmId)
    {
        return atmMachineService.adminAddPaper(addPaperCount, atmId);
    }
    
}
