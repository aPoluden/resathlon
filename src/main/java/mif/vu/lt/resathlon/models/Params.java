package mif.vu.lt.resathlon.models;

public enum Params {
    
    HUNDRED_METERS (25.4347, 18, 1.81, "s"),
    LONG_JUMP(0.14354, 220, 1.4, "m"),
    SHOT_PUT(51.39, 1.5, 1.05, "m"),
    HIGH_JUMP(0.8465, 75, 1.42, "m"),
    FOUR_HUNDRED_METERS(1.53775, 82, 1.81, "s"),
    HURDLES(5.74352, 28.5, 1.92, "s"),
    DISCUS_THROW(12.91, 4, 1.1, "m"),
    POLE_VAULT(0.2797, 100, 1.35, "m"),
    JAVELIN_THROW(10.14, 7, 1.08, "m"),
    THOUSAND_HALF(0.03768, 480, 1.85, "s");
   
    private final double a;
    private final double b;
    private final double c;
    private final String units;
    
    Params(double a, double b, double c, String units) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.units = units;
    }
    
    public double a() { return a; }
    public double b() { return b; }
    public double c() { return c; }
    public String units() { return units; }
}
