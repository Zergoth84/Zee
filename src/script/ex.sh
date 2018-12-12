	echo test
	SERIAL_IND=0
	
	for DEV in ${DEVICES[@]}
	do
		let USER_ID=$SERIAL_IND+1
		echo
		echo -e "$green Adding contacts for User $USER_ID ($red${DEV_NUMBERS[SERIAL_IND]}$green):$default"
		IND=0
		for NAME in ${DEV_NUMBERS[@]}
			do		
			let USER_ID=$IND+1
			if [ "$DEV" != "${DEV_SERIAL[IND]}" ]; then	
				add_contact "User $USER_ID" $NAME
			else
				add_profile_contact "Me (User $USER_ID)" $NAME
			fi	
			let IND=$IND+1
			done
		let SERIAL_IND=$SERIAL_IND+1	
	done