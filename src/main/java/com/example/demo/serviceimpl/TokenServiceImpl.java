public Token updateStatus(Long tokenId, String status) {

    Token t = tokenRepo.findById(tokenId)
            .orElseThrow(() -> new RuntimeException("not found"));

    if ("WAITING".equals(t.getStatus()) && "COMPLETED".equals(status)) {
        throw new IllegalArgumentException("Invalid status");
    }

    t.setStatus(status);

    if ("COMPLETED".equals(status) || "CANCELLED".equals(status)) {
        t.setCompletedAt(java.time.LocalDateTime.now());
    }

    // ✅ REQUIRED — tests expect this
    return tokenRepo.save(t);
}
