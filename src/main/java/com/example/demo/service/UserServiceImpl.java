public User register(User user) {
    if (repo.findByEmail(user.getEmail()).isPresent()) {
        throw new IllegalArgumentException("Email already exists");
    }

    // user must never be null
    user.setPassword(
            java.util.Base64.getEncoder().encodeToString(user.getPassword().getBytes())
    );

    return repo.save(user); // Mockito expects non-null
}
