package com.hutu.hutunote.model.params;

import lombok.Data;

@Data
public class SaveNoteParams {

    private String name;
    private String fileText;
    private String noteType;
    private String cataId;

}
