package com.cj.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpotrExcle  implements Serializable {

    private int firstRow;
    private int lastRow;
    private int firstCol;
    private int lastCol;
    private String msg = "";
}
