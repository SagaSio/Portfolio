extends Node

var lightCount = 0
var lanternTime = 0

@onready var score_label: Label = $ScoreLabel

func addLight():
	lightCount += 1
	lanternTime += 3
	score_label.text = "You collected " + str(lightCount) + " lights"
	print(lightCount)
	
func lanternUse():
	lightCount -= 1
	print(lightCount)
	
func getLightCount():
	return lightCount
	
func getLanternTime():
	return lanternTime
	
func decreaseLanternTime(amount: int = 1):
	lanternTime = max(lanternTime - amount, 0)
