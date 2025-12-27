public interface QueueService {
    QueuePosition updateQueuePosition(Long tokenId, int position);
    QueuePosition getPosition(Long tokenId);
    List<QueuePosition> getQueue();
}
