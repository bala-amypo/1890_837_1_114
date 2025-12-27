public interface ServiceCounterService {
    ServiceCounter addCounter(ServiceCounter sc);
    List<ServiceCounter> getActiveCounters();
}
