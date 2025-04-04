package com.carvajal.catalogue.config;

import com.carvajal.client.ClientMapper;
import com.carvajal.client.ClientRepositoryAdapter;
import com.carvajal.client.ClientUseCaseImp;
import com.carvajal.client.gatewey.out.ClientRepository;
import com.carvajal.client.services.ClientService;
import com.carvajal.car.CarMapper;
import com.carvajal.car.CarRepositoryAdapter;
import com.carvajal.car.CarUseCaseImp;
import com.carvajal.car.gatewey.out.CarRepository;
import com.carvajal.car.services.CarService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UseCaseConfig {
    //Client
    @Bean
    public ClientMapper clientMapper(){
        return new ClientMapper();
    }

    @Bean("clientServicePrimary")
    @Primary
    public ClientService clientService(ClientRepository clientRepository, BCryptPasswordEncoder passwordEncoder) {
        return new ClientService(
                new ClientUseCaseImp(clientRepository, passwordEncoder)
        );
    }

    @Bean
    public ClientRepository clientRepository(ClientRepositoryAdapter clientRepositoryAdapter){
        return clientRepositoryAdapter;
    }

    //Car
    @Bean
    public CarMapper carMapper(){
        return new CarMapper();
    }

    @Bean("carServicePrimary")
    @Primary
    public CarService carService(CarRepository carRepository) {
        return new CarService(
                new CarUseCaseImp(carRepository)
        );
    }

    @Bean
    public CarRepository carRepository(CarRepositoryAdapter carRepositoryAdapter){
        return carRepositoryAdapter;
    }
}
