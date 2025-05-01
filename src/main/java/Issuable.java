public interface Issuable {
    double calculateLateFee();

    boolean isAvailable();

    enum Status {
        BORROWED, PROCESSING, AVAILABLE
    }
}
