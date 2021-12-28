/* LED
 * (Written exclusively for being driven by Arduino, because of design constraints)
 *
 * 		pinNumber (range: Nano: Theoretical: 0-12, A0-A7	)
 * 				  (		  Nano: By design:   A1-A4			)
 * 				  (		  Mega: Theoretical: 0-53, A0-A15	)
 * 				  (		  Mega: By design:   None			)
 * 				  (		  Mux:  By design:   None			)
 *
 * 		bool status
 * 				TRUE:  ON
 * 				FALSE: OFF
 *
 * 		setStatus(bool)
 *
 * ArduinoNano
 */