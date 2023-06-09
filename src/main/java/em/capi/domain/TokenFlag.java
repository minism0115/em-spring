package em.capi.domain;

public enum TokenFlag {

    /**
     * 토큰에 자체 난수 생성기가 있는 경우 설정됩니다.
     */
    Rng("Rng"),

    /**
     * 토큰이 쓰기 금지된 경우 설정됩니다.
     */
    WriteProtected("WriteProtected"),

    /**
     * 사용자가 암호화 기능을 수행하기 위해 로그인해야 하는 경우 설정됩니다.
     */
    LoginRequired("LoginRequired"),

    /**
     * 일반 사용자의 PIN이 초기화된 경우 설정됩니다.
     */
    UserPinInitialized("UserPinInitialized"),

    /**
     * 세션의 암호화 작업 상태를 성공적으로 저장할 때 세션의 상태를 복원하는 데 필요한 모든 키가 "항상" 포함되어 있으면 설정됩니다.
     */
    RestoreKeyNotNeeded("RestoreKeyNotNeeded"),

    /**
     * 토큰에 자체 하드웨어 시계가 있는 경우 설정됩니다.
     */
    ClockOnToken("ClockOnToken"),

    /**
     * 토큰에 "보호된 인증 경로"가 있는 경우 설정되며, 사용자가 Cryptoki 라이브러리를 통해 PIN을 전달하지 않고도 토큰에 로그인할 수
     * 있습니다.
     */
    ProtectedAuthenticationPath("ProtectedAuthenticationPath"),

    /**
     * 토큰이 있는 단일 세션이 이중 암호화 작업을 수행할 수 있는 경우 설정됩니다.
     */
    DualCryptoOperations("DualCryptoOperations"),

    /**
     * 토큰이 이 표준의 범위를 벗어난 C_InitializeToken 또는 이와 동등한 메커니즘을 사용하여 초기화되었다면 설정됩니다.
     * 이 플래그가 설정되어 있을 때 C_InitializeToken을 호출하면 토큰이 다시 초기화됩니다.
     */
    TokenInitialized("TokenInitialized"),

    /**
     * 토큰이 개인 키 개체에 대한 보조 인증을 지원하면 설정됩니다.
     * ("더 이상 사용되지 않습니다. 새 구현에서는 이 플래그를 설정하지 않아야 합니다.)
     */
    SecondaryAuthentication("SecondaryAuthentication"),

    /**
     * 마지막으로 인증에 성공한 이후 잘못된 사용자 로그인 비밀번호를 한 번 이상 입력한 경우 설정됩니다.
     */
    UserPinCountLow("UserPinCountLow"),

    /**
     * 잘못된 사용자 비밀번호를 입력하면 잠김 상태가 됩니다.
     */
    UserPinFinalTry("UserPinFinalTry"),

    /**
     * 사용자 PIN이 잠겨 있으며 토큰에 대한 사용자 로그인이 불가능합니다.
     */
    UserPinLocked("UserPinLocked"),

    /**
     * 사용자 PIN 값이 토큰 초기화 또는 제조 시 설정된 기본값이거나 카드에서 PIN이 만료된 경우 설정됩니다.
     */
    UserPinToBeChanged("UserPinToBeChanged"),

    /**
     * 마지막으로 인증에 성공한 이후 잘못된 SO 로그인 비밀번호를 한 번 이상 입력한 경우 설정됩니다.
     */
    SoPinCountLow("SoPinCountLow"),

    /**
     * 잘못된 SO 비밀번호를 입력하면 잠김 상태가 됩니다.
     */
    SoPinFinalTry("SoPinFinalTry"),

    /**
     * SO PIN이 잠겨 있으며 토큰에 대한 사용자 로그인이 불가능합니다.
     */
    SoPinLocked("SoPinLocked"),

    /**
     * SO PIN 값이 토큰 초기화 또는 제조 시 설정된 기본값이거나 카드에서 PIN이 만료된 경우 설정됩니다.
     */
    SoPinToBeChanged("SoPinToBeChanged");

    TokenFlag(String value) {
        this.value = value;
    }

    private final String value;
}
