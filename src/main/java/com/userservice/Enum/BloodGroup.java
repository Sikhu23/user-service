package com.userservice.Enum;

import java.io.Serializable;

public enum BloodGroup implements Serializable {

    O_POS("O+"), O_NEG("O-"), A_POS("A+"), A_NEG("A-"), B_POS("B+"), B_NEG("B-");

    private String group;

    private BloodGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return this.group;
    }




}
