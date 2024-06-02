package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;
    private UserMapper userMapper;
    private final UserProvider userProvider;

    
    @Autowired // Optional if using constructor injection and only one constructor
    public TrainingServiceImpl(TrainingRepository trainingRepository,UserProvider userProvider) {
        this.trainingRepository = trainingRepository;
        this.userMapper = userMapper;
        this.userProvider = userProvider;

    }
    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }


    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId);  // Implement this method
    }

    @Override
    public List<Training> getTrainingsEndedAfter(Date date) {
        return trainingRepository.findByEndTimeAfter(date);
    }

    @Override
    public List<Training> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }


    public Training addTraining(Long userId, Date startTime, Date endTime, ActivityType activityType, double distance, double averageSpeed) {
        User user = userProvider.getUser(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Training training = new Training(user, startTime, endTime, activityType, distance, averageSpeed);
        return trainingRepository.save(training);
    }

    public Training updateActivityType(Long trainingId, ActivityType newActivityType) {
        Training training = trainingRepository.findById(trainingId).orElseThrow(() -> new IllegalArgumentException("Invalid training ID"));
        training.setActivityType(newActivityType);
        return trainingRepository.save(training);
    }




}
