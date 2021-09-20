import java.io.IOException;
import java.util.Scanner;

public class HomeTask7 {
        public static void main(String[] args) {
            AccuWeatherType accuweatherType = new AccuWeatherType();
               Scanner scanner = new Scanner(System.in);

                while (true) {

                    System.out.println("""
                            Введите 1 для получения погоды в Санкт-Петербурге на сегодня;
                            Введите 5 для получения погоды в Санкт-Петербурге на 5 дней;
                            Для выхода введите 0:""");

                    String command = scanner.nextLine();
                    if((!(command.equals("1")))&&(!(command.equals("5")))&&(!(command.equals("0")))){
                        System.out.println("Неверный номер команды!");
                        break;
                    }
                    if (command.equals("0")) break;

                    try {
                        accuweatherType.getWeather(command);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

