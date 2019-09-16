package com.cj.exclebasics.domain;

import com.cj.common.entity.ExternalEnvirImg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NexusImg {

    private long stationId;

    private String tableName;

    private String year;

    private List<ExternalEnvirImg> oldExternalEnvirImgs;

    private List<ExternalEnvirImg> newExternalEnvirImgs;
}
