package ru.aosges.console;

import org.springframework.beans.factory.annotation.Autowired;
import ru.aosges.impl.OwnerServiceImpl;
import ru.aosges.model.Owner;
import ru.aosges.service.OwnerService;

/**
 * Created by root on 10.03.16.
 */
public class ConsoleMain {

    @Autowired OwnerService ownerService;

    public static void main(String... args) {
        OwnerService ownerService = new OwnerServiceImpl();
        Owner owner = new Owner("+79033082737", "alex@mail.ru", 1123345432);
        ownerService.add(owner);
        System.out.println(owner.getId());
    }
}
