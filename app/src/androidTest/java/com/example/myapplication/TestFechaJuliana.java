package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Date;

import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.utils.Utilities;

public class TestFechaJuliana {
    Tools tool = new Tools();

    @Test
    public void TestFecha(){
        String result = Tools.fechaJuliana(new Date());

        assertEquals("19156", result);
    }

    @Test
    public void TestDigitoVerif(){
        String codigo = "123456789";
        int nDigits = codigo.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--)
        {

            int d = codigo.charAt(i) - '0';

            if (isSecond == true)
                d = d * 2;

            // We add two digits to handle
            // cases that make two digits
            // after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }

        assertEquals(7, (nSum % 10));

    }

    @Test
    public void fechasHabilesACorridas(){

        int result = Utilities.diasHabilesACorridos(10);

        assertEquals(18,result);
    }

}
