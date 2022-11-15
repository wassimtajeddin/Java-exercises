package exercises.chapter6;

public class Animal {
    public int weight;
    public int height;
    public String sound;

    public void printAnimal() {
        System.out.println("animal's weight is " + weight);
        System.out.println("animal's height is " + height);
        System.out.println("animal's sound is " + sound);
    }

    public Animal(int weight, int height, String sound) {
        this.weight = weight;
        this.height = height;
        this.sound = sound;
    }

}
