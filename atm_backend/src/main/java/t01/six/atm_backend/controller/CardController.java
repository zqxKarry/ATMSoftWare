package t01.six.atm_backend.controller;



import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import t01.six.atm_backend.service.impl.CardServiceImpl;
import t01.six.atm_backend.utils.Result;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardServiceImpl cardService;

    @GetMapping("/check-id")
    public Result<?> checkCardId(@RequestParam(defaultValue="")String cardid)
    {
        return cardService.checkCardId(cardid);
    }

    @GetMapping("/check-pass")
    public Result<?> checkCardPassword(@RequestParam(defaultValue="")String cardid,
                                    @RequestParam(defaultValue="")String cardpassword)
    {
        return cardService.checkCardPassword(cardid,cardpassword);
    }

    @GetMapping("/check-bala")
    public Result<?> checkBalance(@RequestParam(defaultValue="")String cardid)
    {
        return cardService.checkBalance(cardid);
    }

    @PostMapping("/user-storermb")
    public Result<?> userStoreRMB(@RequestParam(defaultValue="") String cardid,Integer reallyNum,
                                @RequestParam(defaultValue="") String atmId)
    {
        return cardService.userStoreRMB(cardid, reallyNum, atmId);
    }

    @PostMapping("/user-takermb")
    public Result<?> userTakeRMB(@RequestParam(defaultValue="") String cardid,
                                @RequestParam(defaultValue = "") Double takeAmount,
                                 @RequestParam(defaultValue="") String atmId)
    {
        return cardService.userTakeRMB(cardid, takeAmount, atmId);
    }

    @GetMapping("/get-recentrecord")
    public Result<?> getRecentCardRecord(@RequestParam(defaultValue="")String cardid)
    {
        return cardService.getRecentCardRecord(cardid);
    }

    @GetMapping("/get-onerecord")
    public Result<?> getOneCardRecord(@RequestParam(defaultValue="")String recordid)
    {
        return cardService.getOneCardRecord(recordid);
    }

    @PostMapping("/change-password")
    public Result<?> changePassword(@RequestParam(defaultValue="") String cardid,
                                    @RequestParam(defaultValue = "") String newpassword)
    {
        return cardService.changePassword(cardid,newpassword);
    }

    @PostMapping("/transfer")
    public Result<?> transfer(@RequestParam(defaultValue="") String cardid1,
                              @RequestParam(defaultValue = "") String cardid2,
                             @RequestParam(defaultValue = "") Double amount,
                             @RequestParam(defaultValue = "")String atmId)
    {
        return cardService.transfer(cardid1, amount, cardid2, atmId);
    }
}
