package pl.fifi.MarsRoverApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.fifi.MarsRoverApp.dto.BaseDto;

public interface MarsApiRepository extends JpaRepository<BaseDto, Long>{
        BaseDto findByUserId(Long userId);

}
