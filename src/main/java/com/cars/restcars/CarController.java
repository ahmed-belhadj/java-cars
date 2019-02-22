package com.cars.restcars;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class CarController
{
    private final CarRepository carRepository;
    private final RabbitTemplate rabbitTemplate;

    public CarController(CarRepository carRepository, RabbitTemplate rabbitTemplate)
    {
        this.carRepository = carRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/cars")
    public List<Car> getAll()
    {
        CarLog message = new CarLog("Looked up all cars");
        rabbitTemplate.convertAndSend(RestcarsApplication.QUEUE_NAME, message.toString());
        log.info("Message sent");

        return carRepository.findAll();
    }

    @GetMapping("/cars/id/{id}")
    public Car getById(@PathVariable Long id)
    {
        CarLog message = new CarLog("Looked up car " + id.toString());
        rabbitTemplate.convertAndSend(RestcarsApplication.QUEUE_NAME, message.toString());
        log.info("Message sent");

        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id.toString()));
    }

    @GetMapping("/cars/year/{year}")
    public List<Car> getByYear(@PathVariable Integer year)
    {
        List<Car> temporaryList = new ArrayList<Car>(carRepository.findAll());
        temporaryList.removeIf(car -> car.getYear().equals(year) == false);

        CarLog message = new CarLog("Looked up " + year.toString() + " cars");
        rabbitTemplate.convertAndSend(RestcarsApplication.QUEUE_NAME, message.toString());
        log.info("Message sent");

        return temporaryList;
    }

    @GetMapping("/cars/brand/{brand}")
    public List<Car> getByBrand(@PathVariable String brand)
    {
        List<Car> temporaryList = new ArrayList<Car>(carRepository.findAll());
        temporaryList.removeIf(car -> car.getBrand().toLowerCase().contains(brand.toLowerCase()) == false);

        CarLog message = new CarLog("Looked up " + brand + " cars");
        rabbitTemplate.convertAndSend(RestcarsApplication.QUEUE_NAME, message.toString());
        log.info("Message sent");

        return temporaryList;
    }

    @PostMapping("/cars/upload")
    public List<Car> postCars(@RequestBody List<Car> newCars)
    {
        CarLog message = new CarLog("Data loaded");
        rabbitTemplate.convertAndSend(RestcarsApplication.QUEUE_NAME, message.toString());
        log.info("Message sent");

        return carRepository.saveAll(newCars);
    }

    @DeleteMapping("/cars/delete/{id}")
    public void deleteById(@PathVariable Long id)
    {
        CarLog message = new CarLog(id + "Data deleted");
        rabbitTemplate.convertAndSend(RestcarsApplication.QUEUE_NAME, message.toString());
        log.info("Message sent");

        carRepository.deleteById(id);
    }
}