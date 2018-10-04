package com.kamenov.martin.gosportbg.constants;

import android.graphics.Color;

import static com.google.maps.android.ui.IconGenerator.STYLE_BLUE;
import static com.google.maps.android.ui.IconGenerator.STYLE_GREEN;
import static com.google.maps.android.ui.IconGenerator.STYLE_ORANGE;
import static com.google.maps.android.ui.IconGenerator.STYLE_PURPLE;
import static com.google.maps.android.ui.IconGenerator.STYLE_RED;
import static com.google.maps.android.ui.IconGenerator.STYLE_WHITE;

/**
 * Created by Martin on 28.3.2018 г..
 */

public class Constants {
    public static String[] MONTHS = {"Януари", "Февруари", "Март", "Април",
            "Май", "Юни", "Юли", "Август",
            "Септември", "Октомври", "Ноември", "Декември"};
    public static String[] SPORTS = {"Футбол", "Баскетбол", "Бокс", "Кикбокс",
            "Тенис на маса", "Шах", "Волейбол", "Тенис на корт", "Боен спорт",
            "Катерене", "Планинарство", "Ски", "Бейсбол", "Колоездене",
            "Кънки", "Картинг", "Голф", "Хандбол", "Хокей", "Кънки на лед", "Плуване",
            "Ръгби", "Фехтовка", "Гимнастика", "Пайнтбол",
            "Скуош", "Болинг", "Билярд", "Скейтборд", "Сноуборд", "Крикет",
            "Карти", "Риболов", "Ловен спорт", "Спортно Ходене", "Друг"};
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static String DOMAIN;
    public static int[] STYLES = {STYLE_RED, STYLE_GREEN, STYLE_BLUE, STYLE_ORANGE, STYLE_PURPLE, STYLE_WHITE};
    public static String[] CITIES = new String[] {"София", "Пловдив", "Варна", "Бургас"};
    public static double[][] CITIESCOORDINATES = {{42.698334, 23.319941}, {42.1499994, 24.749997},
            {43.2166658, 27.916663}, {42.499998, 27.4666648}};
    public static int TEAMSIDDIFFERENCE = 10000000;
    public static int MAINCOLOR = Color.parseColor("#282828");
    public static int SECONDCOLOR = Color.parseColor("#ffffff");
    public static int CARDCOLOR = Color.parseColor("#444444");
    public static int CARDTEXTCOLOR = Color.parseColor("#ffffff");
    public static int CLICKEDCARDCOLOR = Color.parseColor("#aaaaaa");
    public static String[] MAP_TYPES = new String[]{"Хибрид", "Нормален"};
    public static int[][] THEMES = new int[][] {{Color.parseColor("#282828"), Color.parseColor("#ffffff")},
            {Color.parseColor("#ffffff"), Color.parseColor("#282828")}};
    public static String[] THEME_NAMES = new String[] {"Черна", "Бяла"};
}
