package com.example.demo.repository;

import com.example.demo.entity.QueuePosition;
import java.util.*;

public interface QueuePositionRepository {

    QueuePosition save(QueuePosition qp);

    Optional<QueuePosition> findByToken_Id(Long tokenId);

    // REQUIRED BY TESTS
    List<QueuePosition> findAll();
}
