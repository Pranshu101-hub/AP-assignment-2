package fleet.interfaces;

public interface Maintainable {
    void scheduleMaintenance();
    boolean needsMaintenance();
    void performMaintenance();
}