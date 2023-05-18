package com.opswat.mem;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

class CallbackStyleExample {
    static List<Shop> shops;

    static {
        shops = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            shops.add(new Shop(String.valueOf(i)));
        }
    }


    public static void main(String[] args) {
        long start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    public static List<String> findPrices(String product) {
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> String.format("%s price is %.2f",
                                        shop.getName(), shop.getPrice(product))))
                        .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }


//    public static List<String> findPrices(String product) { // 1088 1125 1159
//        return shops.parallelStream()
//                .map(shop -> String.format("%s price is %.2f",
//                        shop.getName(), shop.getPrice(product)))
//                .collect(Collectors.toList());
//    }

//    public static List<String> findPrices(String product) { //1370 1402 1282
//        return shops.stream()
//                .map(shop -> String.format("%s price is %.2f",
//                        shop.getName(), shop.getPrice(product)))
//                .collect(Collectors.toList());
//    }
}


@AllArgsConstructor
@Getter
class Shop {
    private String name;

    public Float getPrice(String product) {
        return 10f;
    }
}