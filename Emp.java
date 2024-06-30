package org.example.lesson14;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter // сгенерирует геттеры для всех полей
@Setter
@AllArgsConstructor // конструктор со всеми параметрами
@NoArgsConstructor
public class Emp implements Serializable {
    private String name;
    private int age;
    private String position;

    @Override
    public String toString() {
        return "{ n="+name+", a="+age+" ,p=" + position + "}";
    }
}
