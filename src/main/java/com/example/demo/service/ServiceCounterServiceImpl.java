public ServiceCounter addCounter(ServiceCounter sc) {
    return repo.save(sc); // sc must never be null
}
