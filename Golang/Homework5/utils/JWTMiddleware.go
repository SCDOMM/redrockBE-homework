package utils

import (
	"net/http"
	"strings"

	"github.com/gin-gonic/gin"
)

func LoggerMiddleware() gin.HandlerFunc {
	//检查格式是否正确，，
	return func(c *gin.Context) {
		authHeader := c.GetHeader("Authorization")
		if len(authHeader) == 0 {
			c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"err": "wtf,no auth header"})
			return
		}
		parts := strings.SplitN(authHeader, " ", 2)
		if len(parts) != 2 || parts[0] != "Bearer" {
			c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"err": "not Authorization format"})
			return
		}
		//调用隔壁函数检验token有效性，，，
		tokenString := parts[1]
		claims, err := VerifyAccess(tokenString)
		if err == nil {
			//信息存入Gin Context，相信后人的智慧
			c.Set("userID", claims.UserID)
			c.Set("userRole", claims.Role)
			c.Next()
			return
		}
		//来♂，让我看看refresh
		refreshToken, err := c.Cookie("refresh_token")
		if err != nil {
			//你根本不是refresh，你到底在哪
			c.JSON(200, gin.H{"err": "Refresh Token Error"})
			c.Abort()
			return
		}
		claims, err = VerifyRefresh(refreshToken)
		if err != nil {
			//过期了bro
			c.JSON(400, gin.H{"err": "Invalid Refresh Token,please login again"})
			c.Abort()
			return
		}
		newToken, err := CreateAccessToken(claims.UserID, claims.Role)
		if err != nil {
			c.JSON(400, gin.H{"err": "Create Token Error"})
		}
		claims, err = VerifyAccess(newToken)
		c.Set("userID", claims.UserID)
		c.Set("userRole", claims.Role)
		c.Next()
		return
	}
}
