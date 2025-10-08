extends CharacterBody2D


const SPEED = 80.0
const JUMP_VELOCITY = -250.0

@onready var animated_sprite_2d: AnimatedSprite2D = $AnimatedSprite2D
@onready var point_light_2d: PointLight2D = $PointLight2D

@onready var game_manager: Node = %GameManager

@onready var lantern_timer: Timer = $LanternTimer

@onready var lantern_time_label: Label = $Camera2D/LanternTimeLabel


var isHoldingLight = false


func _physics_process(delta: float) -> void:
	
	lantern_time_label.text = "Time left: " + str(game_manager.getLanternTime())
	
	# Add the gravity.
	if not is_on_floor():
		velocity += get_gravity() * delta

	# Handle jump.
	if Input.is_action_just_pressed("jump") and is_on_floor():
		velocity.y = JUMP_VELOCITY
	
	# Handle light.
	if Input.is_action_just_pressed("light"):
		
		if(!isHoldingLight and game_manager.getLanternTime() > 0):
			isHoldingLight = true
			point_light_2d.enabled = true
			game_manager.lanternUse()
			lantern_timer.start()
			#HERE
			#call for some function to make the lanternTime decrease 
			#with each second.
			#if lanternTime is 0, then isHoldingLight is false
		
		elif(isHoldingLight):
			#stop the timer that decreases the lanternTime
			isHoldingLight = false
			point_light_2d.enabled = false
			lantern_timer.stop()

			

	# Get the input direction and handle the movement/deceleration.
	# As good practice, you should replace UI actions with custom gameplay actions.
	var direction := Input.get_axis("move_left", "move_right")
	
	# If moving left
	if direction > 0:
		animated_sprite_2d.flip_h = true
		
	# If moving right
	if direction < 0:
		animated_sprite_2d.flip_h = false
		
	# ANIMATION
	
	#Not holding light
	# Check if not jumping
	if is_on_floor() and not isHoldingLight:
		if direction == 0:
			animated_sprite_2d.play("Idle")
		else: 
			animated_sprite_2d.play("Run")
			
	elif is_on_floor() and isHoldingLight:
		if direction == 0:
			#animated_sprite_2d.play("Idle")
			animated_sprite_2d.play("IdleLight")
		else: 
			#animated_sprite_2d.play("Run")
			animated_sprite_2d.play("RunLight")
	# If not on floor we're jumping
	elif not is_on_floor() and not isHoldingLight:
		animated_sprite_2d.play("Jump")
	else:
		animated_sprite_2d.play("JumpLight")
		
	
	
	
	if direction:
		velocity.x = direction * SPEED
	else:
		velocity.x = move_toward(velocity.x, 0, SPEED)

	move_and_slide()


func _on_lantern_timer_timeout() -> void:
	game_manager.decreaseLanternTime()
	if game_manager.getLanternTime() <= 0:
		isHoldingLight = false
		point_light_2d.enabled = false
		lantern_timer.stop()
