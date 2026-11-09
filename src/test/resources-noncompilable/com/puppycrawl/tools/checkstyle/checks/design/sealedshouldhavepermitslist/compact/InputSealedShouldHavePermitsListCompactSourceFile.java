/*
SealedShouldHavePermitsList


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

// violation below 'Sealed classes or interfaces should explicitly declare permitted subclasses'
sealed class SealedClassNoPermits {
}

final class SubClass extends SealedClassNoPermits {
}

sealed class SealedClassWithPermits permits PermittedSubClass {
}

final class PermittedSubClass extends SealedClassWithPermits {
}

// violation below 'Sealed classes or interfaces should explicitly declare permitted subclasses'
sealed interface SealedInterfaceNoPermits {
}

final class SubInterfaceImpl implements SealedInterfaceNoPermits {
}

sealed interface SealedInterfaceWithPermits permits PermittedInterfaceImpl {
}

final class PermittedInterfaceImpl implements SealedInterfaceWithPermits {
}
