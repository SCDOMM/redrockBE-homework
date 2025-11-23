package utils

import (
	"errors"
	"fmt"
	"strings"
	"time"

	"github.com/golang-jwt/jwt/v5"
)

var StandardHeader = []byte(`{"alg":"HS256","typ":"JWT"}`)
var (
	accessSecret  = []byte("你是一个一个一个，，，")
	refreshSecret = []byte("哼哼哼，哼啊啊啊啊啊")
	issuer        = "你爹"
	accessTTL     = 1 * time.Second
	refreshTTL    = 24 * time.Hour
)

type Claims struct {
	UserID uint64 `json:"user_id"`
	Role   string `json:"role"`
	Type   string `json:"type"`
	jwt.RegisteredClaims
}

func CreateToken(ID uint64, role string) (accessToken string, refreshToken string, err error) {
	now := time.Now()

	accessClaims := Claims{
		UserID: ID,
		Role:   role,
		Type:   "access",
		RegisteredClaims: jwt.RegisteredClaims{
			Issuer:    issuer,
			Subject:   fmt.Sprintf("%d", ID),
			Audience:  []string{"user"},
			ExpiresAt: jwt.NewNumericDate(now.Add(accessTTL)),
			NotBefore: jwt.NewNumericDate(now.Add(-5 * time.Second)),
			IssuedAt:  jwt.NewNumericDate(now),
		},
	}
	accessTok := jwt.NewWithClaims(jwt.SigningMethodHS256, accessClaims)
	accessToken, err = accessTok.SignedString(accessSecret)
	if err != nil {
		return "", "", fmt.Errorf("sign access token: %w", err)
	}

	refreshClaims := Claims{
		UserID: ID,
		Role:   role,
		Type:   "refresh",
		RegisteredClaims: jwt.RegisteredClaims{
			Issuer:    issuer,
			Subject:   fmt.Sprintf("%d", ID),
			Audience:  []string{"user"},
			ExpiresAt: jwt.NewNumericDate(now.Add(refreshTTL)),
			NotBefore: jwt.NewNumericDate(now.Add(-5 * time.Second)),
			IssuedAt:  jwt.NewNumericDate(now),
		},
	}
	refreshTok := jwt.NewWithClaims(jwt.SigningMethodHS256, refreshClaims)
	refreshToken, err = refreshTok.SignedString(refreshSecret)
	if err != nil {
		return "", "", fmt.Errorf("sign refresh token: %w", err)
	}
	return accessToken, refreshToken, nil
}
func CreateAccessToken(ID uint64, role string) (accessToken string, err error) {
	now := time.Now()
	accessClaims := Claims{
		UserID: ID,
		Role:   role,
		Type:   "access",
		RegisteredClaims: jwt.RegisteredClaims{
			Issuer:    issuer,
			Subject:   fmt.Sprintf("%d", ID),
			Audience:  []string{"user"},
			ExpiresAt: jwt.NewNumericDate(now.Add(accessTTL)),
			NotBefore: jwt.NewNumericDate(now.Add(-5 * time.Second)),
			IssuedAt:  jwt.NewNumericDate(now),
		},
	}
	accessTok := jwt.NewWithClaims(jwt.SigningMethodHS256, accessClaims)
	accessToken, err = accessTok.SignedString(accessSecret)
	if err != nil {
		return "", fmt.Errorf("sign access token: %w", err)
	}
	return accessToken, nil
}
func VerifyAccess(accessT string) (*Claims, error) {
	raw := strip(accessT)
	token, err := jwt.ParseWithClaims(raw, &Claims{}, func(t *jwt.Token) (interface{}, error) {
		if _, ok := t.Method.(*jwt.SigningMethodHMAC); !ok || t.Method.Alg() != jwt.SigningMethodHS256.Alg() {
			return nil, fmt.Errorf("unexpected signing method: %v", t.Header["alg"])
		}
		return accessSecret, nil
	}, jwt.WithLeeway(5*time.Second))
	if err != nil {
		return nil, err
	}

	claims, ok := token.Claims.(*Claims)
	if !ok || !token.Valid {
		return nil, errors.New("invalid access token")
	}
	fmt.Println(claims.Type)
	if claims.Type != "access" {
		return nil, errors.New("not an access token")
	}
	return claims, nil
}

func VerifyRefresh(RefreshT string) (*Claims, error) {

	raw := strip(RefreshT)

	token, err := jwt.ParseWithClaims(raw, &Claims{}, func(t *jwt.Token) (interface{}, error) {
		if _, ok := t.Method.(*jwt.SigningMethodHMAC); !ok || t.Method.Alg() != jwt.SigningMethodHS256.Alg() {
			return nil, fmt.Errorf("unexpected signing method: %v", t.Header["alg"])
		}
		return refreshSecret, nil
	}, jwt.WithLeeway(5*time.Second))
	if err != nil {
		return nil, err
	}

	claims, ok := token.Claims.(*Claims)
	if !ok || !token.Valid {
		return nil, errors.New("invalid refresh token")
	}
	if claims.Type != "refresh" {
		return nil, errors.New("not an refresh token")
	}
	return claims, nil
}

func strip(s string) string {
	if strings.HasSuffix(strings.ToLower(strings.TrimSpace(s)), "bearer") {
		return strings.TrimSpace(s[len("bearer"):])
	}
	return strings.TrimSpace(s)
}

//func CreateJWT(data1, data2 []byte, secret string) string {
//	Header := base64.URLEncoding.EncodeToString(data1)
//	Header = strings.TrimRight(Header, "=")
//	Payload := base64.URLEncoding.EncodeToString(data2)
//	Payload = strings.TrimRight(Payload, "=")
//	A := Header + "." + Payload
//	mac := hmac.New(sha256.New, []byte(secret))
//	mac.Write([]byte(A))
//	Signature := base64.URLEncoding.EncodeToString(mac.Sum(nil))
//	Signature = strings.TrimRight(Signature, "=")
//	return Header + "." + Payload + "." + Signature
//}
