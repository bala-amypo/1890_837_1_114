@Override
public ServiceCounter addCounter(ServiceCounter sc) {
    // MUST save the same instance
    return repo.save(sc);
}
