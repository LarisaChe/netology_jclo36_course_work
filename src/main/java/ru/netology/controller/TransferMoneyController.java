package ru.netology.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.netology.exception.ErrorOperationId;
import ru.netology.service.TransferMoneyService;
import ru.netology.transfer.Code;
import ru.netology.transfer.TransferMoney;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin//(origins = "https://serp-ya.github.io/card-transfer/", maxAge = 3600)
@RestController
public class TransferMoneyController {
    private final TransferMoneyService service;

    public TransferMoneyController(TransferMoneyService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    @ResponseBody
    public ResponseTransfer transfer(@RequestBody @Validated TransferMoney transferMoney) throws ErrorOperationId, IOException {
        ResponseTransfer response = new ResponseTransfer(service.createOperation(transferMoney));
        return response;
    }

    @PostMapping("/confirmOperation")
    @ResponseBody
    public ResponseTransfer confirmOperation(@RequestBody Code code) throws IOException {
        //System.out.println(code.toString());
        ResponseTransfer response = new ResponseTransfer(service.confirm(code.getOperationId(), code.getCode()));
        return response;
    }

    @CrossOrigin("http://localhost")
    @GetMapping("/operations")
    public ConcurrentHashMap<String, TransferMoney> getOperations() {
        return service.getOperations();
    }
}
