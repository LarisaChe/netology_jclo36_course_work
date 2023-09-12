package ru.netology.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.netology.exception.ErrorOperationId;
import ru.netology.service.TransferMoneyService;
import ru.netology.transfer.Code;
import ru.netology.transfer.TransferMoney;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin//(origins = "https://serp-ya.github.io/card-transfer/", maxAge = 3600)
@RestController
public class TransferMoneyController {
    private final TransferMoneyService service;

    public TransferMoneyController(TransferMoneyService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public Map<String, String> transfer(@RequestBody @Validated TransferMoney transferMoney) throws ErrorOperationId, IOException {
        String operationId = service.createOperation(transferMoney);
        Map<String, String> response = new HashMap<>();
        response.put("operationId", operationId);
        return response;
    }

    @PostMapping("/confirmOperation")
    public Map<String, String> confirmOperation(@RequestBody Code code) throws IOException {
        //System.out.println(code.toString());
        String operationId = service.confirm(code.getOperationId(), code.getCode());
        Map<String, String> response = new HashMap<>();
        response.put("operationId", operationId);
        return response;
    }

    @CrossOrigin("http://localhost")
    @GetMapping("/operations")
    public ConcurrentHashMap<String, TransferMoney> getOperations() {
        return service.getOperations();
    }
}
