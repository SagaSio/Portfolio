extends Area2D

const FILEBEGIN = "res://scenes/level"

func _on_body_entered(body: Node2D) -> void:
	if body.is_in_group("Player"):
		var currentSceneFile = get_tree().current_scene.scene_file_path
		var nextLevelNum = currentSceneFile.to_int() + 1
		var nextLevelPath =  FILEBEGIN + str(nextLevelNum) + ".tscn"
		
		get_tree().change_scene_to_file(nextLevelPath)
