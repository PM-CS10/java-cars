package com.lambdaschool.cars.carssprint;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController

public class CarController {
    private final CarRepo carRepos;
    private final RabbitTemplate rt;

    public CarController(CarRepo carRepos, RabbitTemplate rt) {
        this.carRepos = carRepos;
        this.rt = rt;
    }
    @GetMapping("cars/id/{id}")
    public List carById(@PathVariable Long id) {
        Car car = carRepos.findById(id).orElseThrow();
        List cars = new ArrayList();
        cars.add(car);
        return cars;
    }
    @GetMapping("/cars/year/{year}")
    public List<Car> carByYear(@PathVariable int year) {
        return carRepos.findAll().stream().filter(car -> car.getYear() == year).collect(Collectors.toList());
    }
    @GetMapping("/cars/brand/{brand}")
    public List<Car> carByBrand(@PathVariable String  brand) {
        return carRepos.findAll().stream().filter(car -> car.getBrand().toLowerCase().equals(brand.toLowerCase())).collect(Collectors.toList());
    }

    @PostMapping("/cars/upload")
    public List<Car> newCountry(@RequestBody List<Car> newCountries){
        return carRepos.saveAll(newCountries);
    }

    @DeleteMapping("/cars/delete/{id}")
    public Car deleteById(@PathVariable Long id) {
        Car car = carRepos.findById(id).orElseThrow();
        carRepos.delete(car);
        return car;
    }
}
