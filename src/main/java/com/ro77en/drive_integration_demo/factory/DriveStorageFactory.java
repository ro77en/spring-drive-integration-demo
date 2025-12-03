package com.ro77en.drive_integration_demo.factory;

import com.ro77en.drive_integration_demo.enums.DriveProvider;
import com.ro77en.drive_integration_demo.strategy.DriveStorageStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DriveStorageFactory {

    private final Map<DriveProvider, DriveStorageStrategy> strategies;

    public DriveStorageFactory(List<DriveStorageStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(DriveStorageStrategy::getProvider, Function.identity()));
    }

    public DriveStorageStrategy getStrategy(DriveProvider provider) {
        return strategies.get(provider);
    }
}
