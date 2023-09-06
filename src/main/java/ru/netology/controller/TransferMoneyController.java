package ru.netology.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.netology.exception.ErrorOperationId;
import ru.netology.exception.ErrorVerificationCode;
import ru.netology.service.TransferMoneyService;
import ru.netology.transfer.TransferMoney;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
@CrossOrigin(origins = "https://serp-ya.github.io/card-transfer", maxAge = 3600)
@RestController
public class TransferMoneyController {
    private TransferMoneyService service;

    public TransferMoneyController(TransferMoneyService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody @Validated TransferMoney transferMoney) throws ErrorOperationId, IOException {
        return service.createOperation(transferMoney);
    }

    @PostMapping("/confirmOperation")
    public String confirmOperation(@RequestParam("operationId") String operationId) throws ErrorOperationId, ErrorVerificationCode, IOException {
        return service.confirm(operationId);
    }
   /* @PostMapping("/confirmOperation")
    public String confirmOperation(@RequestBody String operationId) throws ErrorOperationId, ErrorVerificationCode, IOException {
        return service.confirm(operationId);
    }*/

    @GetMapping("/operations")
    public ConcurrentHashMap<String, TransferMoney> getOperations() {
        return service.getOperations();
    }
}
