# Resolving with simple multidimensional linear regression with help of sklearn
# f(x) = (asinx + blnx)^2 + cx^2 =
# = a^2*sin(x)^2 + 2*a*b*sin(x)*ln(x) + b^2*ln(x)^2 + c*x^2
# = t1*sin(x)^2 + t2*sin(x)*ln(x) + t3*ln(x)^2 + t4*x^2
# a = t1^1/2; b = t3^1/2; c = t4

from sklearn.linear_model import LinearRegression
import math

x = []
y = []

for line in open("coefficient_recovery.txt"):
    xx, yy = map(float, line.strip().split())
    x.append(xx)
    y.append(yy)

X = []
for xx in x:
    currFeatures = [
        math.sin(xx) ** 2, # sin(x)^2
        math.sin(xx)* math.log(xx), # sin(x)*ln(x)
        math.log(xx) ** 2, # ln(x)^2
        xx ** 2 # x^2
    ]
    X.append(currFeatures)

model = LinearRegression()
model.fit(X, y)

coef = model.coef_

a = math.sqrt(coef[0])
b = math.sqrt(coef[2])
c = coef[3]

print(a, b, c)
