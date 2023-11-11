package t01.six.atm_backend.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import t01.six.atm_backend.utils.Result;

@RestController
@RequestMapping("/card")
public class CardController {
    
    @GetMapping("/check-id")
    public Result<?> checkCardId(@RequestParam(defaultValue="")String cardid)
    {
        return null;
    }

    @GetMapping("/check-pass")
    public Result<?> checkCardPassword(@RequestParam(defaultValue="")String cardid,
                                    @RequestParam(defaultValue="")String cardpassword)
    {
        return null;
    }

    @GetMapping("/check-bala")
    public Result<?> checkBalance(@RequestParam(defaultValue="")String cardid)
    {
        return null;
    }

    @GetMapping("/print-voucher")
    public Result<?> printVoucher(@RequestParam(defaultValue="")String recordid,
                                  @RequestParam(defaultValue="") String atmId)
    {
        return null;
    }

    @PostMapping("/user-addrmb")
    public Result<?> userAddRMB(@RequestParam(defaultValue="") String cardid,
                                @RequestParam(defaultValue="") Double addAmount,
                                @RequestParam(defaultValue="") String atmId)
    {
        return null;
    }

    @PostMapping("/user-takermb")
    public Result<?> userTakeRMB(@RequestParam(defaultValue="") String cardid,
                                @RequestParam(defaultValue = "") Double takeAmount,
                                 @RequestParam(defaultValue="") String atmId)
    {
        return null;
    }

    @GetMapping("/get-allrecord")
    public Result<?> getAllCardRecord(@RequestParam(defaultValue="")String cardid)
    {
        return null;
    }

    @PostMapping("/change-password")
    public Result<?> changePassword(@RequestParam(defaultValue="") String cardid,
                                    @RequestParam(defaultValue = "") String newpassword)
    {
        return null;
    }

    @PostMapping("/transfer")
    public Result<?> transfer(@RequestParam(defaultValue="") String cardid1,
                              @RequestParam(defaultValue = "") String cardid2,
                             @RequestParam(defaultValue = "")String atmId)
    {
        return null;
    }
}
