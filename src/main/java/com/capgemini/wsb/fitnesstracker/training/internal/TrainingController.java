package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

import java.util.List;


@RestController
@RequestMapping("/v1/trainings")
@Slf4j
public class TrainingController {


    private final UserMapper userMapper;

    private final TrainingMapper trainingMapper;

    private final TrainingServiceImpl trainingService;


    public TrainingController(UserMapper userMapper, TrainingMapper trainingMapper, TrainingServiceImpl trainingService) {
        this.userMapper = userMapper;
        this.trainingMapper = trainingMapper;

        this.trainingService = trainingService;
    }

    @GetMapping
    public List<TrainingDto> getAllTrainings(){
        return trainingService.getAllTrainings().stream().map(trainingMapper::toDto).toList();


    }

    @GetMapping("/user")
    public List<TrainingDto> getTrainingsByUserId(@RequestParam Long userId) {
        List<Training> trainings = trainingService.getTrainingsByUserId(userId);
        return trainings.stream().map(trainingMapper::toDto).toList();  // Convert to DTOs
    }

    @GetMapping("/ended-after")
    public List<TrainingDto> getTrainingsEndedAfter(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Training> trainings = trainingService.getTrainingsEndedAfter(date);
        return trainings.stream().map(trainingMapper::toDto).toList();
    }

    @GetMapping("/by-activity-type")
    public List<TrainingDto> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        List<Training> trainings = trainingService.getTrainingsByActivityType(activityType);
        return trainings.stream().map(trainingMapper::toDto).toList();
    }

    @PostMapping
    public ResponseEntity<TrainingDto> addTraining(@RequestBody TrainingDto trainingDto) {
        log.info("Training request received: {}", trainingDto);
        Training training = trainingService.addTraining(
                trainingDto.getUserDto().Id(),
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed()
        );
        TrainingDto responseDto = trainingMapper.toDto(training);
        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("/{id}/activity-type")
    public ResponseEntity<TrainingDto> updateActivityType(@PathVariable Long id, @RequestBody TrainingDto trainingDto) {
        Training updatedTraining = trainingService.updateActivityType(id, trainingDto.getActivityType());
        TrainingDto responseDto = trainingMapper.toDto(updatedTraining);
        return ResponseEntity.ok(responseDto);
    }



}
