#!/bin/bash
#Enabling job control.


#Clearing old shit.
clear

#Initializing colour information.
#Colour names are as they seem.
#https://en.wikipedia.org/wiki/ANSI_escape_code contains what colours look like.
BLACK="\e[0;30m"
RED="\e[0;31m"
GREEN="\e[0;32m"
BROWNORANGE="\e[0;33m"
BLUE="\e[0;34m"
PURPLE="\e[0;35m"
CYAN="\e[0;36m"
LIGHTGRAY="\e[0;37m"
DARKGRAY="\e[0;90m"
LIGHTRED="\e[0;91m"
LIGHTGREEN="\e[0;92m"
YELLOW="\e[0;93m"
LIGHTBLUE="\e[0;94m"
LIGHTPURPLE="\e[0;95m"
LIGHTCYAN="\e[0;96m"
WHITE="\e[0;97m"

#Will remove color. Use this at the end of the thing you want coloured.
NC="\e[0m"
#Use the format "${RED} TEXT ${NC}"
#Using "echo -e -n" will enable the colour to be read and no newline to be created.

#ColorsOn = 1 is on. 0 is off.
colorsOn=1

#RED is for failure.
#YELLOW is for warnings.
#GREEN will be used for successful things.
#LIGHTCYAN is used for what the input expects.
#WHITE is used for informational stuff.
#LIGHTPURPLE is used for prompts.

#Defaulting colour
echo -e -n ${NC}

while true;
do
	#Providing the full menu of options. Expecting a numeric return.
	echo -e ${LIGHTPURPLE}"Please select one of the ${LIGHTCYAN}numeric options${LIGHTPURPLE}:"
	echo -e ${LIGHTCYAN}"1${NC}. File Operations"
	echo -e ${LIGHTCYAN}"2${NC}. User Operations"
	echo -e ${LIGHTCYAN}"3${NC}. Locating Information"
	echo -e ${LIGHTCYAN}"4${NC}. Process Menu"
	echo -e ${LIGHTCYAN}"5${NC}. Fun Menu"
	echo -e ${LIGHTCYAN}"88${NC}. Exit"
	echo -e ${LIGHTCYAN}"99${NC}. Shut Down"
	echo -e ${LIGHTCYAN}"3030${NC}. Color Options"
	echo -e -n ${LIGHTCYAN}
	read mainMenuResponse
	
	#Defaulting colour
	echo -e -n ${NC}
	#Clearing screen.
	clear
	
	#Main Menu Options - This case statement validates that the input is numeric.
	case $mainMenuResponse in
		#File Operations Submenu
		1)
			#Defaulting the directory.
			cd /root/
			while true;
			do
				#Displaying file operation submenu
				echo -e ${WHITE}"Current Working Directory:" $(pwd)
				
				echo -e ${LIGHTPURPLE}"Please select a ${LIGHTCYAN}numeric option${LIGHTPURPLE} within the File Operation submenu:"
				echo -e ${LIGHTCYAN}"1${NC}. Create a file"
				echo -e ${LIGHTCYAN}"2${NC}. Delete a file"
				echo -e ${LIGHTCYAN}"3${NC}. Create a directory"
				echo -e ${LIGHTCYAN}"4${NC}. Delete a directory"
				echo -e ${LIGHTCYAN}"5${NC}. Create a symbolic link"
				echo -e ${LIGHTCYAN}"6${NC}. Change ownership of a file"
				echo -e ${LIGHTCYAN}"7${NC}. Change permissions on a file"
				echo -e ${LIGHTCYAN}"8${NC}. Modify text within a file"
				echo -e ${LIGHTCYAN}"9${NC}. Copy a file"
				echo -e ${LIGHTCYAN}"10${NC}. Rename a file"
				echo -e ${LIGHTCYAN}"88${NC}. Return to the main menu"
				echo -e ${LIGHTCYAN}"99${NC}. Shut down"
				echo -e ${LIGHTCYAN}"150${NC}. Change current working directory"
				echo -e -n ${LIGHTCYAN}
				read fileOperationSubmenu
				
				#Defaulting colour
				echo -e -n ${NC}
				clear
				
				
				#Checking if a directory has been previously provided.
				#This should run only during the first run-through.
				if [ -v $givenDirectory ]
				then
					if [[ "$fileOperationSubmenu" -eq 88 || "$fileOperationSubmenu" -eq 99 || "$fileOperationSubmenu" -eq 150 ]]
					then
						#Do nothing
						clear
					else
						#Prompt for working directory.
						echo -e ${LIGHTPURPLE}"Please specify the ${LIGHTCYAN}working directory${LIGHTPURPLE}. All actions following will be relative to this."
						echo -e ${LIGHTPURPLE}"This expects the format to be: ${LIGHTCYAN}/XXX/XXX/XXX/${NC}"
						echo -e ${YELLOW}"(The directory will be created if it does not exist.)"
						echo -e -n ${LIGHTCYAN}
						read givenDirectory
						
						
						#Defaulting colour.
						echo -e -n ${NC}
						#Clearing screen.
						clear
						
						#DIRECTORY VALIDATION
						
						#Checking if it is a forward slash (/) - Continuing if it is, adding it if there is not one.
						if [[ $givenDirectory = */ ]]
						then
							#DO NOTHING. MIGHT BE VALID.
							clear
						else
							#Adding the slash.
							givenDirectory="${givenDirectory}/"
						fi
						
						#Ensuring the directory is valid. Creating it if it does not exist.
						if [ -d $givenDirectory ]
						then
							#Changing to directory.
							cd $givenDirectory
						else 
							#Telling user that the directory was not found, but is going to be created.
							echo -e ${RED}"Directory not found. Creating directory..."
							#Creating directory.
							mkdir -p $givenDirectory
							#Announcing that the directory is created.
							echo -e ${GREEN}$givenDirectory "created!"
							
							#Changing to directory.
							cd $givenDirectory
						fi
						
						
					fi		
				else
					#Doing nothing.
					clear
				fi
				
				#File Operation submenu
				case $fileOperationSubmenu in
					#Create a file.
					1)
						#Showing files in the CWD.
						echo -e -n ${WHITE}
						ls -la
						#Asking user for filename for created file.
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}file name${LIGHTPURPLE} would you like to use?"${NC}
						
						echo -e -n ${LIGHTCYAN}
						read fileName
						
						#Input validation
						fileName=$(echo $fileName | sed -e 's/[^A-Za-z0-9._-]/_/g')
						
						#Creating the file.
						touch $fileName
						
						#Clearing screen.
						clear
						
						#Informing user about completion.
						echo -e ${LIGHTCYAN}$fileName${GREEN} "created in:" ${WHITE}$givenDirectory${NC}
						;;
					#Delete a file
					2)
						#Showing files in the CWD.
						echo -e -n ${WHITE}
						ls -la
						#Asking user for filename of file to delete.
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}file${LIGHTPURPLE} would you like to delete?"${NC}
						echo -e -n ${LIGHTCYAN}
						read fileName
						
						#Ensuring the file is an actual file.
						if [ -d $fileName ]
						then
							#Informing user that a directory was selected.
							clear
							echo -e -n ${RED}
							echo "Warning: Selected item is a directory. No changes made."
							echo "Please select a file."
							echo -e -n ${NC}
						elif [ -L $fileName ]
						then
							#Warning user about a symbolic link being deleted.
							
							echo -e ${YELLOW}"Warning: Selected item is a symbolic link."
							echo -e "Are you sure you want to delete" ${LIGHTCYAN}$fileName${NC} "?"
							
							echo -e "${LIGHTCYAN}Yes${NC} or ${LIGHTCYAN}No${NC}?"
							echo -e -n ${LIGHTCYAN}
							read yesNoAnswer
							
							#Reading the answer with the first letter of the input.
							case ${yesNoAnswer:0:1} in
								'Y')
									#Removing file
									rm -f $fileName
									
									#Clearing screen.
									clear
									
									#Informing user what file was deleted.
									echo -e ${LIGHTCYAN}$fileName${GREEN} "deleted!"${NC}
									;;
								'y')
									#Removing file
									rm -f $fileName
									
									#Clearing screen.
									clear
									
									#Informing user what file was deleted.
									echo -e ${LIGHTCYAN}$fileName${GREEN} "deleted!"${NC}
									;;
								'N')
									clear
									#Notifying user and restarting delete operation.
									echo -e ${WHITE}"No changes made. Please try again."${NC}
									;;
								'n')
									clear
									#Notifying user and restarting delete operation.
									echo -e ${WHITE}"No changes made. Please try again."${NC}
									;;
								*)
									clear
									#Catch all, no "yes" or "no" given.
									echo -e ${RED}"Invalid option:" ${LIGHTCYAN}$yesNoAnswer${NC}
									echo -e ${RED}"No changes made. Please try again."${NC}
									;;
							esac
						elif [ -e $fileName ]
						then
							#Removing file
							rm -f $fileName
							
							#Clearing screen.
							clear
							
							#Informing user what file was deleted.
							echo -e ${LIGHTCYAN}$fileName${GREEN} "deleted!"${NC}
						else
							clear
							#Informing user that file was not found.
							echo -e ${RED}"File:" ${LIGHTCYAN}$fileName${RED} "not found. Please try again."${NC}
							
						fi
						;;
					3)
						#Create a directory.
						echo -e -n ${WHITE}
						ls -la
						#Asking user for the directories name.
						echo -e ${LIGHTPURPLE}"What should the directories ${LIGHTCYAN}name${LIGHTPURPLE} be?"
						echo -e ${YELLOW}"(Absolute file paths can not be specified here.)"
						echo -e -n ${LIGHTCYAN}
						read directoryName
						
						#Input validation.
						directoryName=$(echo $directoryName | sed -e 's/[^A-Za-z0-9._-]/_/g')
						
						#Checking if directory exists.
						if [ -d $directoryName ]
						then
							clear
							#Notifying user that directory already exists.
							echo -e ${YELLOW}"The directory:" ${LIGHTCYAN}$directoryName${YELLOW} "already exists within" ${WHITE}$givenDirectory${NC}
							echo -e ${YELLOW}"No changes made."${NC}
						else
							#Creating directory.
							mkdir -p $directoryName
						
							#Clearing screen.
							clear
						
							#Informing the user that the directory has been created and where it was created at.
							echo -e ${LIGHTCYAN}$directoryName${GREEN} "created at" ${WHITE}$givenDirectory${NC}
						fi
						
						;;
					4)
						#Delete a directory.
						echo -e -n ${WHITE}
						ls -la
						
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}directory${LIGHTPURPLE} would you like to delete?"
						echo -e ${YELLOW}"Warning: This will happen recursively and delete items within the directory!"${NC}
						echo -e -n ${LIGHTCYAN}
						read directoryName
						
						echo -e -n ${NC}
						
						#Is directory valid?
						if [ -d $directoryName ]
						then
							#Remove the directory.
							rm -r -f $directoryName
							
							#Clearing screen.
							clear
							
							#Informing user that the directory was deleted and where it was located at.
							echo -e ${GREEN}"Directory" ${LIGHTCYAN}$directoryName ${GREEN}"deleted in"${WHITE} $givenDirectory${NC}
						elif [ -e $directoryName ]
						then
						
							echo -e ${LIGHTPURPLE}"A file has been selected. Would you like to delete this file? ${LIGHTCYAN}Y/N"
							read yesNoAnswer
							
							echo -e -n ${NC}
							
							#Reading the answer with the first letter of the input.
							case ${yesNoAnswer:0:1} in
								'Y')
									#Removing file
									rm -f $directoryName
									
									#Clearing screen.
									clear
									
									#Informing user what file was deleted.
									echo -e ${GREEN}"File:" ${LIGHTCYAN}$directoryName ${GREEN}"deleted!"${NC}
									;;
								'y')
									#Removing file
									rm -f $directoryName
									
									#Clearing screen.
									clear
									
									#Informing user what file was deleted.
									echo -e ${GREEN}"File:" ${LIGHTCYAN}$directoryName ${GREEN}"deleted!"${NC}
									;;
								'N')
									clear
									#Notifying user and restarting delete operation.
									echo -e ${RED}"No changes made. Please try again."${NC}
									;;
								'n')
									clear
									#Notifying user and restarting delete operation.
									echo -e ${RED}"No changes made. Please try again."${NC}
									;;
								*)
									clear
									#Catch all, no "yes" or "no" given.
									echo -e ${RED}"Invalid option:" ${LIGHTCYAN}$yesNoAnswer${NC}
									echo -e ${RED}"No changes made. Please try again."${NC}
									;;
							esac
						fi
						;;
					5)
						#Create a symbolic link
						echo -e -n ${WHITE}
						ls -la
						#Asking for what file the link links to.
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}file${LIGHTPURPLE} would you like to ${LIGHTCYAN}link to${LIGHTPURPLE}? Provide the full path if it is outside of the current working directory."
						echo -e -n ${LIGHTCYAN}
						read fileToLink
						echo -e -n ${NC}
						#Asking for the name of the link.
						echo -e ${LIGHTPURPLE}"What would you the ${LIGHTCYAN}name${LIGHTPURPLE} of the ${LIGHTCYAN}link${LIGHTPURPLE} to be? ${YELLOW}(Include the extension)"
						echo -e -n ${LIGHTCYAN}
						read fileName
						echo -e -n ${NC}
						#Input validation
						fileName=$(echo $fileName | sed -e 's/[^A-Za-z0-9._-]/_/g')
						
						if [ -e $fileToLink ] && [ ! -e $fileName ]
						then
							#Creating the symbolic link.
							ln -s $fileToLink $fileName
							
							clear
							
							#Informing user that the link was created
							echo -e ${GREEN}"Link" $fileName ${GREEN}"created. Linking to" ${LIGHTCYAN}$fileToLink${NC}
						elif [ ! -e $fileToLink ] && [ -e $fileName]
						then
							clear
							#Informing the user that both options were fucked!
							echo -e ${RED}"The file you want to link to ("${RED}") was not found."
							echo -e ${RED}"Additionally, the link name ("${LIGHTCYAN}$fileName${RED}") is already used."
							echo -e ${RED}"No changes made. Please try again."${NC}
						elif [ ! -e $fileToLink ]
						then
							clear
							#The file to link to is fucked!
							echo -e ${RED}"The file you want to link to ("${LIGHTCYAN}$fileToLink${RED}") was not found."
							echo -e ${RED}"No changes made. Please try again."${NC}
						elif [ -e $fileName ]
						then
							clear
							#The filename is fucked!
							echo -e ${RED}"The link name ("${LIGHTCYAN}$fileName${RED}") is already used."
							echo -e ${RED}"No changes made. Please try again."${NC}
						fi
						;;
					6)
						#Change ownership of a file
						echo -e -n ${WHITE}
						ls -la
						echo -e -n ${NC}
						#Init counter.
						counter=0
						#Creating safetly loop.
						while true;
						do
							#After 5 failed attempts, reprint the ls -la.
							if [ $counter -eq 5 ]
							then
								echo -e -n ${WHITE}
								ls -la
								echo -e -n ${NC}
								let "counter = 0"
							fi
							echo -e "${LIGHTPURPLE}What ${LIGHTCYAN}file${LIGHTPURPLE} would you like to change the ownership of?"
							echo -e -n ${LIGHTCYAN}
							read fileName
							echo -e -n ${NC}
							#Verifying file exists...
							if [ -e $fileName ]
							then	
								break
							else
								echo -e ${RED}"File:" ${LIGHTCYAN}$fileName${RED} "not found. Please try again."
							fi
							let "counter++"
						done
						clear
						
						#Showing users list.
						echo -e -n ${WHITE}
						cat /etc/passwd
						echo -e -n ${NC}
						
						#Asking for username.
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}user${LIGHTPURPLE} would you like to use? ${YELLOW}(A user will be created if it does not exist)"
						echo -e ${YELLOW}"(A default group will be created for the new user.)"
						echo -e -n ${LIGHTCYAN}
						read givenUser
						tempVariable=$(getent passwd | cut -d: -f1 | grep $givenUser)
						
						#Verifying the user exists. Creating the user if they do not exist.
						if [ ! -z $tempVariable ]
						then
							clear
						else
							clear
							#Notifying user that the user was not found.
							echo -e ${RED}"User:" ${LIGHTCYAN}$givenUser${RED} "not found."
							echo -e ${GREEN}"Creating user..."
							echo -e ${LIGHTPURPLE}"Would you like a comment for the new user?"
							echo -e "Enter the comment now, if so."
							echo -e "Otherwise, press enter."
							echo -e -n ${LIGHTCYAN}
							read userComment
							
							#If the comment is empty then create the user
							if [ -z userComment ]
							then
								#Creating user without a comment.
								echo -e ${GREEN}"Creating user..."
								useradd $givenUser
								read temp
							else
								#Creating user with provided comment.
								useradd -c \"$userComment\" $givenUser
								
								clear
								
								#Informing user that the user was created with the comment.
								echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "created with the comment:" ${LIGHTCYAN}$userComment
							fi
						fi
						
						#Showing groups list.
						echo -e -n ${WHITE}
						cat /etc/group
						echo -e -n ${NC}
						#Asking for group.
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}group${LIGHTPURPLE} would you like to use? ${YELLOW}(A group will be created if it does not exist)"
						echo -e -n ${LIGHTCYAN}
						read givenGroup
						tempVariable=$(getent group | cut -d: -f1 | grep $givenGroup)
						
						#Verifying the user exists. Creating the user if they do not exist.
						if [ ! -z $tempVariable ]
						then
							#GROUP FOUND
							echo -e ${GREEN}"GROUP FOUND" ${NC}
						else
							clear
							#Notifying user that the user was not found.
							echo -e ${RED}"Group:" ${LIGHTCYAN}$givenGroup${RED} "not found."
							echo -e ${GREEN}"Creating group..."
							
							#Creating user without a comment.
							groupadd $givenGroup
							
							clear
							
							#Informing user that the user was created with the comment.
							echo -e ${GREEN}"Group:" ${LIGHTCYAN}$givenGroup${GREEN} "created!"${NC}
							
						fi
						
						
						
						#Changing ownership of provided file to be of the provided user and group.
						chown $givenUser"."$givenGroup $fileName
						
						clear
						
						#Informing user of change 
						echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "and group:" ${LIGHTCYAN}$givenGroup${GREEN} "now own the file:" ${LIGHTCYAN}$fileName${GREEN} "within directory:"  ${WHITE}$givenDirectory${NC}
						;;
					7)
						#Change permissions on a file
						echo -e -n ${WHITE}
						ls -la
						#Asking user what file needs to be modified.
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}file${LIGHTPURPLE} would you like to alter the permissions on?"
						echo -e -n ${LIGHTCYAN}
						read fileName
						
						#Asking user for permissions.
						echo -e ${LIGHTPURPLE}"Please enter what ${LIGHTCYAN}permissions you want the file to have:"
						echo -e ${LIGHTPURPLE}"Either" \"${LIGHTCYAN}"RWXRWXRWX"${LIGHTPURPLE}\" "or" \"${LIGHTCYAN}"###"${LIGHTPURPLE}\"". ${YELLOW}Dashes may be used for the text variation."
						echo -e -n ${LIGHTCYAN}
						read permissions
						echo -e -n ${NC}
						
						clear
						#Verifying that the file exists.
						if [ -e $fileName ]
						then
							#Verify whether permissions length is 3 or 7 characters.
							if [ ${#permissions} -eq 9 ]
							then
								#Initializing permission value variables.
								ownerPermission=0
								ownerGroupPermission=0
								otherPermission=0
								#Forcing the input text to be all uppercase.
								permissions=${permissions^^}
								#TEXT VERSION WITH 9 CHARACTERS.
								for (( counter=0; counter<9; counter++ ))
								do
									#Running through every character of the provided string.
									case ${permissions:counter:1} in
										'R')
											#Checking where the R is found.
											case $counter in
												0)
													let "ownerPermission += 4"
													clear
													;;
												3)
													let "ownerGroupPermission += 4"
													clear
													;;
												6)
													let "otherPermission += 4"
													clear
													;;
												*)
													#This means that the R character was found in another position than 0, 3, or 6.
													break
													clear
													echo -e ${LIGHTPURPLE}"Invalid option provided at character:" ${LIGHTCYAN}$counter${LIGHTPURPLE}". Please use the format" \"${LIGHTCYAN}RWXRWXRWX${LIGHTPURPLE}\"
													;;
											esac
											;;
										'W')
											#Checking where the W is found.
											case $counter in
												1)
													let "ownerPermission += 2"
													clear
													;;
												4)
													let "ownerGroupPermission += 2"
													clear
													;;
												7)
													let "otherPermission += 2"
													clear
													;;
												*)
													#This means that the R character was found in another position than 1, 4, or 7.
													break
													clear
													echo -e ${LIGHTPURPLE}"Invalid option provided at character:" ${LIGHTCYAN}$counter${LIGHTPURPLE}". Please use the format" \"${LIGHTCYAN}RWXRWXRWX${LIGHTPURPLE}\"
													;;
											esac
											;;
										'X')
											#Checking where the X is found.
											case $counter in
												2)
													let "ownerPermission += 1"
													;;
												5)
													let "ownerGroupPermission += 1"
													;;
												8)
													let "otherPermission += 1"
													;;
												*)
													#This means that the R character was found in another position than 2, 5, or 8.
													break
													clear
													echo -e ${LIGHTPURPLE}"Invalid option provided at character:" ${LIGHTCYAN}$counter${LIGHTPURPLE}". Please use the format" \"${LIGHTCYAN}RWXRWXRWX${LIGHTPURPLE}\"
													;;
											esac
											;;
										*)
											#No R W or X found. Likely because it is not meant to be given that spot a permission, such as a hyphen (-).
											;;
									esac
								done
								
								#Ensuring that every permission conversion results in a valid number.
								if [[ $ownerPermission =~ ^[0-7]+$ ]] && [[ $ownerGroupPermission =~ ^[0-7]+$ ]] && [[ $otherPermission =~ ^[0-7]+$ ]]
								then
									#Changing permission with text version (converte to ints) of permissions given.
									chmod $ownerPermission$ownerGroupPermission$otherPermission $fileName
									
									clear
								
									#Informing user of changes to permissions.
									echo -e ${GREEN}"File:" ${LIGHTCYAN}$fileName${GREEN} "permissions have been changed to:" ${LIGHTCYAN}$ownerPermission$ownerGroupPermission$otherPermission${GREEN} "or" ${LIGHTCYAN}$permissions${NC}
								else
									#Accounting for unknown errors.
									echo -e ${RED}"Conversion error when converting text:" \"${LIGHTCYAN}$permissions${RED}\" "to numbers. Please try again."${NC}
								fi
							
							
							elif [ ${#permissions} == 3 ]
							then
								
								#3 NUMBERS PROVIDED. Verify they are valid numbers (0-7)
								#The +$ seems to mean it checks the whole string.
								if [[ $permissions =~ ^[0-7]+$ ]]
								then
									#Changing persmissions with given permissions number.
									chmod $permissions $fileName
									
									clear
									
									#Informing user of permissions changed.
									echo -e ${LIGHTCYAN}$fileName${GREEN} "permissions changed to:" ${LIGHTCYAN}$permissions${NC}
								else
									#Invalid number range provided.
									clear
									echo -e ${RED}"Invalid numbers values provided for permissions. Please use 3 digits with the characters 1 through 7."${NC}
									echo -e ${RED}"No changes made. Please try again."${NC}
								fi
							else
								#Informing user that the length was wrong, nothing was done.
								echo -e ${RED}"Invalid character length provided:" ${LIGHTCYAN}${#permissions}${RED}". Please enter 3 digits, or 9 characters."
								echo -e ${RED}"No changes made. Please try again."${NC}
							fi
						else
							#Telling user that the file was not found.
							echo -e ${RED}"File:" \"${LIGHTCYAN}$fileName${RED}\" "not found. Please try again."${NC}
						fi
						;;
					8)
						#Showing files..
						echo -e -n ${WHITE}
						ls -la
						#Modify text within a file (use vim)
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}file${LIGHTPURPLE} would you like to edit? ${YELLOW}(This uses vim)"${NC}
						echo -e -n ${LIGHTCYAN}
						read fileName
						
						#Checking if file exists.
						if [ -e $fileName ]
						then
							#Allowing user to edit the file.
							vim $fileName
							clear
							echo -e ${GREEN}"File:" \"${LIGHTCYAN}$fileName${GREEN}\" "editted!"
						else
							echo -e ${RED}"File:" \"${LIGHTCYAN}$fileName${RED}\" "not found. Please try again."${NC}
						fi
						
						;;
					9)
						#Copy a file
						echo -e -n ${WHITE}
						ls -la
						echo "Current directory:"
						pwd
						echo -e -n ${NC}
						
						#Asking what the source file is.
						echo -e ${LIGHTPURPLE}"What is the ${LIGHTCYAN}file${LIGHTPURPLE} you would like to copy?"
						echo -e ${YELLOW}"(You may enter an ${LIGHTCYAN}absolute path${YELLOW} here.)"
						echo -e -n ${LIGHTCYAN}
						read fileName
						echo -e -n ${NC}
						
						#Verifying source file exists.
						if [ -e $fileName ]
						then
							#Asking for the destination.
							echo -e ${LIGHTCYAN}"Where${LIGHTPURPLE} would you like the file to be copied to?"
							echo -e ${YELLOW}"(You may enter an ${LIGHTCYAN}absolute path${YELLOW}, or just the ${LIGHTCYAN}new name${YELLOW} of the ${LIGHTCYAN}file${YELLOW} in the current directory."${NC}
							echo -e -n ${LIGHTCYAN}
							read fileDestination
							echo -e -n ${NC}
							
							clear
							
							#Checking that an input was provided.
							if [ ! -z $fileDestination ]
							then
								#Checking if an absolute path was defined. Otherwise, input validation is performed which removes /
								if [[ ${fileDestination:0:1} = '/' ]]
								then
									#copy the file.
									cp -r $fileName $fileDestination
									
									clear
									
									#Informing user of copy.
									echo -e ${GREEN}"Copied:" \"${LIGHTCYAN}$fileName${GREEN}\" "to:" \"${LIGHTCYAN}$fileDestination${GREEN}\"
								else
									#Input Validation
									fileDestination=$(echo $fileDestination | sed -e 's/[^A-Za-z0-9._-]/_/g')
									
									#copy the file.
									
									cp -r $fileName $fileDestination
									
									clear
									
									#Informing user of copy.
									echo -e ${GREEN}"Copied:" \"${LIGHTCYAN}$fileName${GREEN}\" "to:" \"${LIGHTCYAN}$givenDirectory$fileDestination${GREEN}\"
								fi
							else
								#No destination provided.
								echo -e ${RED}"No input given for the destination. Please try again."${NC}
							fi
							
						else
							#Informing user that the file they want to copy does not exist.
							echo -e ${RED}"File:" ${LIGHTCYAN}$fileName${RED} "not found. No actions taken."
							echo -e ${RED}"Please try again."${NC}
						fi
						;;
					10)
						#Rename a file
						echo -e -n ${WHITE}
						ls -la
						#Asking for source file to be renamed.
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}file${LIGHTPURPLE} would you like to rename?"
						echo -e ${YELLOW}"If one file path is absolute, use absolute paths for both."
						echo -e ${YELLOW}"(This also functions as a move, if you do not change the name but change the absolute path directory for the destination)"
						
						echo -e -n ${LIGHTCYAN}
						read fileName
						
						#Checking if the provided file exists.
						if [ -e $fileName ]
						then
							#Asking user for new name for the file.
							echo -e ${LIGHTPURPLE}"What would you like the ${LIGHTCYAN}new files name${LIGHTPURPLE} to be?"
							echo -e -n ${LIGHTCYAN}
							read newFileName
							echo -e -n ${NC}
							#Checking if an absolute path was provided.
							if [[ ${newFileName:0:1} = '/' ]]
							then
								#Renaming file.
								mv $fileName $newFileName
								
								clear
								
								#Informing user of move/rename.
								echo -e ${GREEN}"Renamed/Moved:" \"${LIGHTCYAN}$fileName${GREEN}\" "to:" \"${LIGHTCYAN}$newFileName${GREEN}\"${NC}
							else
								#Input Validation
								newFileName=$(echo $newFileName | sed -e 's/[^A-Za-z0-9._-]/_/g')
								
								#Renaming file.
								mv $fileName $newFileName
								
								clear
								
								#Informing user of copy.
								echo -e ${GREEN}"Renamed/Moved:" \"${LIGHTCYAN}$fileName${GREEN}\" "to:" \"${LIGHTCYAN}$givenDirectory$newFileName${GREEN}\"${NC}
							fi
						else
							#Invalid source file.
							echo -e ${RED}"File:" ${LIGHTCYAN}$fileName${RED} "not found. Please try again."${NC}
						fi
						;;
					88)
						#Exit the File Operations submenu.
						break
						;;
					99)
						#Shutdown
						echo -e ${GREEN}"Powering off!"${NC}
						poweroff
						;;
					150)
						#Change CWD
						#Prompt for working directory.
						echo -e ${LIGHTPURPLE}"Please specify the ${LIGHTCYAN}working directory${LIGHTPURPLE}. All actions following will be relative to this."
						echo -e ${LIGHTPURPLE}"This expects the format to be: ${LIGHTCYAN}/XXX/XXX/XXX/${NC}"
						echo -e ${YELLOW}"(The directory will be created if it does not exist.)"
						echo -e -n ${LIGHTCYAN}
						read givenDirectory
						
						
						#Defaulting colour.
						echo -e -n ${NC}
						#Clearing screen.
						clear
						
						#DIRECTORY VALIDATION
						
						#Checking if it is a forward slash (/) - Continuing if it is, adding it if there is not one.
						if [[ $givenDirectory = */ ]]
						then
							#DO NOTHING. MIGHT BE VALID.
							clear
						else
							#Adding the slash.
							givenDirectory="${givenDirectory}/"
						fi
						
						#Ensuring the directory is valid. Creating it if it does not exist.
						if [ -d $givenDirectory ]
						then
							#Changing to directory.
							cd $givenDirectory
						else 
							#Telling user that the directory was not found, but is going to be created.
							echo -e ${RED}"Directory not found. Creating directory..."
							#Creating directory.
							mkdir -p $givenDirectory
							#Announcing that the directory is created.
							echo -e ${GREEN}$givenDirectory "created!"
							
							#Changing to directory.
							cd $givenDirectory
						fi
						;;
					esac
			done
			;;
		#User Operations Submenu
		2)
			#Defaulting the directory.
			cd /root/
			while true;
			do
				#Displaying user operation submenu
				echo -e ${LIGHTPURPLE}"Please select a ${LIGHTCYAN}numeric option${LIGHTPURPLE} within the User Operation submenu:"
				echo -e ${LIGHTCYAN}"1${NC}. Create a user"
				echo -e ${LIGHTCYAN}"2${NC}. Change the group for a user"
				echo -e ${LIGHTCYAN}"3${NC}. Create a group"
				echo -e ${LIGHTCYAN}"4${NC}. Delete a user"
				echo -e ${LIGHTCYAN}"5${NC}. Change a password"
				echo -e ${LIGHTCYAN}"88${NC}. Return to Main Menu"
				echo -e ${LIGHTCYAN}"99${NC}. Shut down"
				echo -e -n ${LIGHTCYAN}
				read userOperationSubmenu
				echo -e -n ${NC}
				
				clear
				#User Operation submenu
				case $userOperationSubmenu in
					1)
						#Asking for username.
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}name${LIGHTPURPLE} would you like the ${LIGHTCYAN}user${LIGHTPURPLE} to have?"
						echo -e ${YELLOW}"(A default group will be created for the new user.)"
						echo -e -n ${LIGHTCYAN}
						read givenUser
						
						#Seeing if the user exists.
						tempVariable=""
						tempVariable=$(getent passwd | cut -d: -f1 | grep $givenUser)
						
						#Verifying the user exists. Creating the user if they do not exist.
						if [ ! -z $tempVariable ]
						then
							clear
						else
							clear
							#Notifying user that the user was not found.
							echo -e ${LIGHTPURPLE}"Would you like a ${LIGHTCYAN}comment for the new user?"
							echo -e ${LIGHTPURPLE}"Enter the comment now, if so."
							echo -e ${LIGHTPURPLE}"Otherwise, press enter."
							read userComment
							
							#If the comment is empty then create the user
							if [ -z userComment ]
							then
								#Creating user without a comment.
								echo -e ${GREEN}"Creating user..."
								useradd $givenUser
							else
								#Creating user with provided comment.
								useradd -c \"$userComment\" $givenUser
								
								clear
								
								#Informing user that the user was created with the comment.
								echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "created with the comment:" ${LIGHTCYAN}$userComment
								echo -e -n ${WHITE}
								id $givenUser
								echo -e -n ${NC}
							fi
						fi
						;;
					2)
						#Asking the user what user they want to affect
						echo -e -n ${WHITE}
						cat /etc/passwd
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}user${LIGHTPURPLE} would you like to use?"
						echo -e -n ${LIGHTCYAN}
						read givenUser
						echo -e -n ${NC}
						clear
						
						#Finding if user exists.
						tempVariable=""
						tempVariable=$(getent passwd | cut -d: -f1 | grep $givenUser)
						
						if [ ! -z $tempVariable ]
						then
							#User exists
							#Asking user what group they would like to give the user.
							echo -e -n ${WHITE}
							cat /etc/group
							echo -e ${LIGHTPURPLE}"What group should user" ${LIGHTCYAN}$givenUser${LIGHTPURPLE} "be in?"
							echo -e ${YELLOW}"A new group will be created if it does not exist."
							read givenGroup
							tempVariable=""
							tempVariable=$(getent group | cut -d: -f1 | grep $givenGroup)
							
							#Verifying the user exists. Creating the user if they do not exist.
							if [ ! -z $tempVariable ]
							then
								#GROUP FOUND
								#Applying user to group.
								usermod -g $givenGroup $givenUser
								
								clear
								
								#Informing user of user/group stuff.
								echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "now in group:" ${LIGHTCYAN}$givenGroup${NC}
							else
								clear
								
								echo -e ${RED}"Group:" ${LIGHTCYAN}$givenGroup${RED} "not found." "Would you like to ${LIGHTCYAN}create${RED} this group?"
								echo -e -n ${LIGHTCYAN}
								read yesNoAnswer
							
								case ${yesNoAnswer:0:1} in
									'Y')
										clear
										#Notifying user that the user was not found.
										echo -e ${RED}"Group:" ${LIGHTCYAN}$givenGroup${RED} "not found."
										echo -e ${GREEN}"Creating group..."
										
										#Creating user without a comment.
										groupadd $givenGroup
										
										clear
										
										#Informing user that the user was created with the comment.
										echo -e ${GREEN}"Group:" ${LIGHTCYAN}$givenGroup${GREEN} "created!"${NC}
										
										#Applying user to group.
										usermod -g $givenGroup $givenUser
										
										clear
										
										#Informing user of user/group stuff.
										echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "now in group:" ${LIGHTCYAN}$givenGroup${NC}
										;;
									'y')
										clear
										#Notifying user that the user was not found.
										echo -e ${RED}"Group:" ${LIGHTCYAN}$givenGroup${RED} "not found."
										echo -e ${GREEN}"Creating group..."
										
										#Creating user without a comment.
										groupadd $givenGroup
										
										clear
										
										#Informing user that the user was created with the comment.
										echo -e ${GREEN}"Group:" ${LIGHTCYAN}$givenGroup${GREEN} "created!"${NC}
										
										#Applying user to group.
										usermod -g $givenGroup $givenUser
										
										clear
										
										#Informing user of user/group stuff.
										echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "now in group:" ${LIGHTCYAN}$givenGroup${NC}
										;;
									'N')
										clear
										#Notifying user and restarting operation.
										echo -e ${RED}"No changes made. Please try again."${NC}
										;;
									'n')
										clear
										#Notifying user and restarting operation.
										echo -e ${RED}"No changes made. Please try again."${NC}
										;;
									*)
										clear
										#Catch all, no "yes" or "no" given.
										echo -e ${RED}"Invalid option:" ${LIGHTCYAN}$yesNoAnswer${NC}
										echo -e ${RED}"No changes made. Please try again."${NC}
										;;
								esac
							fi
						else
							#User does not exist.
							echo -e ${RED}"User:" ${LIGHTPURPLE}$givenUser${RED} "not found."
							echo -e ${LIGHTPURPLE}"Would you like to ${LIGHTCYAN}create${LIGHTPURPLE} the user?"
							echo -e -n ${LIGHTCYAN}
							read yesNoAnswer
							echo -e -n ${NC}
							
							case ${yesNoAnswer:0:1} in
								'Y')
									echo -e ${LIGHTPURPLE}"Would you like a ${LIGHTCYAN}comment${LIGHTPURPLE} for the new user?"
									echo -e ${LIGHTPURPLE}"Enter the comment now, if so."
									echo -e ${LIGHTPURPLE}"Otherwise, press enter."
									echo -e -n ${LIGHTCYAN}
									read userComment
									echo -e -n ${NC}
									
									#If the comment is empty then create the user
									if [ -z userComment ]
									then
										#Creating user without a comment.
										echo -e ${GREEN}"Creating user..."
										useradd $givenUser
									else
										#Creating user with provided comment.
										useradd -c \"$userComment\" $givenUser
										
										clear
										
										#Informing user that the user was created with the comment.
										echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "created with the comment:" ${LIGHTCYAN}$userComment${NC}
									fi
									#User exists
									#Asking user what group they would like to give the user.
									echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}group${LIGHTPURPLE} should user" ${LIGHTCYAN}$givenUser${LIGHTPURPLE} "be in?"
									echo -e -n ${LIGHTCYAN}
									read givenGroup
									echo -e -n ${NC}

									tempVariable=""
									tempVariable=$(getent group | cut -d: -f1 | grep $givenGroup)
									
									#Verifying the user exists. Creating the user if they do not exist.
									if [ ! -z $tempVariable ]
									then
										#GROUP FOUND
										#Applying user to group.
										usermod -g $givenGroup $givenUser
										
										clear
										
										#Informing user of user/group stuff.
										echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "now in group:" ${LIGHTCYAN}$givenGroup${NC}
									else
										clear
										#Notifying user that the user was not found.
										echo -e ${RED}"Group:" ${LIGHTCYAN}$givenGroup${RED} "not found."
										echo -e ${GREEN}"Creating group..."
										
										#Creating user without a comment.
										groupadd $givenGroup
										
										clear
										
										#Informing user that the user was created with the comment.
										echo -e ${GREEN}"Group:" ${LIGHTCYAN}$givenGroup${GREEN} "created!"
									
									fi							
									
									;;
								'y')
									echo -e ${LIGHTPURPLE}"Would you like a ${LIGHTCYAN}comment${LIGHTPURPLE} for the new user?"
									echo -e ${LIGHTPURPLE}"Enter the comment now, if so."
									echo -e ${LIGHTPURPLE}"Otherwise, press enter."
									echo -e -n ${LIGHTCYAN}
									read userComment
									echo -e -n ${NC}
									
									#If the comment is empty then create the user
									if [ -z userComment ]
									then
										#Creating user without a comment.
										echo -e ${GREEN}"Creating user..."
										useradd $givenUser
									else
										#Creating user with provided comment.
										useradd -c \"$userComment\" $givenUser
										
										clear
										
										#Informing user that the user was created with the comment.
										echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "created with the comment:" ${LIGHTCYAN}$userComment${NC}
									fi
									#User exists
									#Asking user what group they would like to give the user.
									echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}group${LIGHTPURPLE} should user" ${LIGHTCYAN}$givenUser${LIGHTPURPLE} "be in?"
									echo -e -n ${LIGHTCYAN}
									read givenGroup
									echo -e -n ${NC}

									tempVariable=""
									tempVariable=$(getent group | cut -d: -f1 | grep $givenGroup)
									
									#Verifying the user exists. Creating the user if they do not exist.
									if [ ! -z $tempVariable ]
									then
										#GROUP FOUND
										#Applying user to group.
										usermod -g $givenGroup $givenUser
										
										clear
										
										#Informing user of user/group stuff.
										echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "now in group:" ${LIGHTCYAN}$givenGroup${NC}
									else
										clear
										#Notifying user that the user was not found.
										echo -e ${RED}"Group:" ${LIGHTCYAN}$givenGroup${RED} "not found."
										echo -e ${GREEN}"Creating group..."
										
										#Creating user without a comment.
										groupadd $givenGroup
										
										clear
										
										#Informing user that the user was created with the comment.
										echo -e ${GREEN}"Group:" ${LIGHTCYAN}$givenGroup${GREEN} "created!"
									
									fi							
									
									;;
								'N')
									clear
									#Notifying user and restarting operation.
									echo -e ${RED}"No changes made. Please try again."
									;;
								'n')
									clear
									#Notifying user and restarting operation.
									echo -e ${RED}"No changes made. Please try again."
									;;
								*)
									clear
									#Catch all, no "yes" or "no" given.
									echo -e ${RED}"Invalid option:" ${LIGHTCYAN}$yesNoAnswer
									echo -e ${RED}"No changes made. Please try again."
									;;
							esac
						fi
						;;
					3)
						echo -e ${LIGHTPURPLE}"What would you like the groups ${LIGHTCYAN}name${LIGHTPURPLE} to be?"
						echo -e -n ${LIGHTCYAN}
						read givenGroup
						echo -e -n ${NC}
						tempVariable=""
						tempVariable=$(getent group | cut -d: -f1 | grep $givenGroup)
						
						#Verifying the user exists. Creating the user if they do not exist.
						if [ ! -z $tempVariable ]
						then
							#GROUP FOUND
							echo -e ${GREEN}"Group already exists. No changes were made."
						else
							clear
							#Notifying user that the user was not found.
							echo -e ${RED}"Group:" ${LIGHTCYAN}$givenGroup${RED} "not found."
							echo -e ${GREEN}"Creating group..."
							
							#Creating user without a comment.
							groupadd $givenGroup
							
							clear
							
							#Informing user that the user was created with the comment.
							echo -e ${GREEN}"Group:" ${LIGHTCYAN}$givenGroup${GREEN} "created!"
							
						fi
						;;
					4)
						#Asking user what user they want to delete
						echo -e -n ${WHITE}
						cat /etc/passwd
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}user${LIGHTPURPLE} would you like to delete?"
						echo -e -n ${LIGHTCYAN}
						read givenUser
						echo -e -n ${NC}
						
						#Seeing if the user exists.
						tempVariable=""
						tempVariable=$(getent passwd | cut -d: -f1 | grep $givenUser)
						
						#Verifying the user exists. Creating the user if they do not exist.
						if [ ! -z $tempVariable ]
						then
							clear
							
							#Deleting user
							userdel $givenUser
							
							#Telling user.
							echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "deleted!"
						else
							clear
							#Notifying user that the requested user was not found.
							echo -e ${RED}"User:" ${LIGHTCYAN}$givenUser${RED} "not found. No action taken."
						fi
						;;
					5)
						#Asking what user they want to change the password of.
						echo -e -n ${WHITE}
						cat /etc/passwd
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}user${LIGHTPURPLE} would you like to change the password for?"
						echo -e -n ${LIGHTCYAN}
						read givenUser
						echo -e -n ${NC}
						
						#Seeing if the user exists.
						tempVariable=""
						tempVariable=$(getent passwd | cut -d: -f1 | grep $givenUser)
						
						#Verifying the user exists. Creating the user if they do not exist.
						if [ ! -z $tempVariable ]
						then
							clear
							
							#Changing password of given user
							passwd $givenUser
							
							#Telling user.
							echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "password changed!"
						else
							clear
							#Notifying user that the requested user was not found.
							echo -e ${RED}"User:" ${LIGHTCYAN}$givenUser${RED} "not found."
							echo -e ${LIGHTPURPLE}"Would you like to ${LIGHTCYAN}create${LIGHTPURPLE} the user? Y/N"
							echo -e -n ${LIGHTCYAN}
							read yesNoAnswer
							echo -e -n ${NC}
							
							case ${yesNoAnswer:0:1} in
								'Y')
									echo -e ${LIGHTPURPLE}"Would you like a ${LIGHTCYAN}comment${LIGHTPURPLE} for the new user?"
									echo -e ${LIGHTPURPLE}"Enter the comment now, if so."
									echo -e ${LIGHTPURPLE}"Otherwise, press enter."
									echo -e -n ${LIGHTCYAN}
									read userComment
									
									#If the comment is empty then create the user
									if [ -z userComment ]
									then
										#Creating user without a comment.
										echo -e ${GREEN}"Creating user..."
										useradd $givenUser
									else
										#Creating user with provided comment.
										useradd -c \"$userComment\" $givenUser
										
										clear
										
										#Informing user that the user was created with the comment.
										echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "created with the comment:" ${LIGHTCYAN}$userComment${NC}
										
									fi
									
									#Changing password of given user
									passwd $givenUser
									
									#Telling user.
									echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "password changed!"
									;;
								'y')
									echo -e ${LIGHTPURPLE}"Would you like a ${LIGHTCYAN}comment${LIGHTPURPLE} for the new user?"
									echo -e ${LIGHTPURPLE}"Enter the comment now, if so."
									echo -e ${LIGHTPURPLE}"Otherwise, press enter."
									echo -e -n ${LIGHTCYAN}
									read userComment
									
									#If the comment is empty then create the user
									if [ -z userComment ]
									then
										#Creating user without a comment.
										echo -e ${GREEN}"Creating user..."
										useradd $givenUser
									else
										#Creating user with provided comment.
										useradd -c \"$userComment\" $givenUser
										
										clear
										
										#Informing user that the user was created with the comment.
										echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "created with the comment:" ${LIGHTCYAN}$userComment${NC}
										
									fi
									
									#Changing password of given user
									passwd $givenUser
									
									#Telling user.
									echo -e ${GREEN}"User:" ${LIGHTCYAN}$givenUser${GREEN} "password changed!"
									;;
								'N')
									clear
									#Notifying user and restarting operation.
									echo -e ${RED}"No changes made. Please try again."
									;;
								'n')
									clear
									#Notifying user and restarting operation.
									echo -e ${RED}"No changes made. Please try again."
									;;
								*)
									clear
									#Catch all, no "yes" or "no" given.
									echo -e ${RED}"Invalid option:" ${LIGHTCYAN}$yesNoAnswer
									echo -e ${RED}"No changes made. Please try again."
									;;
							esac
							
						fi
						;;
					88)
						#Exit the User Operations submenu.
						clear
						break
						;;
					99)
						#Shutdown
						echo -e ${GREEN}"Powering off!"${NC}
						poweroff
						;;
				esac
			done
			;;
		#Locating Information Subemnu
		3)
			cd /root/
			while true;
			do
				#Displaying user operation submenu
				echo -e ${LIGHTPURPLE}"Please select a ${LIGHTCYAN}numeric option${LIGHTPURPLE} within the User Operation submenu:"
				echo -e ${LIGHTCYAN}"1${NC}. Find text within a file"
				echo -e ${LIGHTCYAN}"2${NC}. Show information about a user account"
				echo -e ${LIGHTCYAN}"3${NC}. List the contents of a directory"
				echo -e ${LIGHTCYAN}"88${NC}. Return to Main Menu"
				echo -e ${LIGHTCYAN}"99${NC}. Shut down"
				echo -e -n ${LIGHTCYAN}
				read userOperationSubmenu
				echo -e -n ${NC}
				
				#User Operation submenu
				case $userOperationSubmenu in
					#Find text within a file.
					1)
						clear
						#Change CWD
						#Prompt for working directory.
						echo -e ${LIGHTPURPLE}"Please specify the ${LIGHTCYAN}working directory${LIGHTPURPLE}. All actions following will be relative to this."
						echo -e ${LIGHTPURPLE}"This expects the format to be: ${LIGHTCYAN}/XXX/XXX/XXX/${NC}"
						echo -e ${YELLOW}"(The directory will be created if it does not exist.)"
						echo -e -n ${LIGHTCYAN}
						read givenDirectory
						
						
						#Defaulting colour.
						echo -e -n ${NC}
						#Clearing screen.
						clear
						
						#DIRECTORY VALIDATION
						
						#Checking if it is a forward slash (/) - Continuing if it is, adding it if there is not one.
						if [[ $givenDirectory = */ ]]
						then
							#DO NOTHING. MIGHT BE VALID.
							clear
						else
							#Adding the slash.
							givenDirectory="${givenDirectory}/"
						fi
						
						#Ensuring the directory is valid. Creating it if it does not exist.
						if [ -d $givenDirectory ]
						then
							#Changing to directory.
							cd $givenDirectory
						else 
							#Telling user that the directory was not found, but is going to be created.
							echo -e ${RED}"Directory not found. Creating directory..."
							#Creating directory.
							mkdir -p $givenDirectory
							#Announcing that the directory is created.
							echo -e ${GREEN}$givenDirectory "created!"
							
							#Changing to directory.
							cd $givenDirectory
						fi
						
						echo -e -n ${WHITE}
						ls -la
						echo -e -n ${NC}
						#Asking user what file they want to search.
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}file${LIGHTPURPLE} would you like to search?"
						echo -e -n ${LIGHTCYAN}
						read fileToSearch
						
						#Checking if a value was provided.
						if [ ! -z $fileToSearch ]
						then
							#Checking if file exists.
							if [ -e $fileToSearch ]
							then
								#File found.
								#Asking for search term.
								echo -e ${LIGHTPURPLE}"What would you like to ${LIGHTCYAN}search${LIGHTPURPLE} for within" ${LIGHTCYAN}$fileToSearch${LIGHTPURPLE}"?"
								echo -e ${YELLOW}"(Line numbers will be shown on the left)"
								echo -e ${YELLOW}"(This is case-sensitive)"
								echo -e -n ${LIGHTCYAN}
								read searchTerm
								
								#Checking if search term is given.
								if [ ! -z $searchTerm ]
								then
									#Input given.
									echo -e ${GREEN}
									grep -n "$searchTerm" "$fileToSearch"
									echo -e -n ${YELLOW}
									read -n 1 -p "Press any key to continue..."
									echo -e -n ${NC}
									clear
								else
									clear
									#No input provided.
									echo -e ${RED}"No search term provided. Please try again."$(NC)
								fi
							else
								clear
								#Telling user that the file was not found.
								echo -e ${RED}"File:" \"${LIGHTCYAN}$fileToSearch${RED}\" "not found. Please try again"${NC}
							fi
						else
							clear
							#Telling user that no input was provided.
							echo -e ${RED}"No input provided. Please try again."${NC}
						fi
						;;
					2)
						clear
						#Informing user and adding a 5 second timer.
						echo -e ${YELLOW}"All users will be shown soon. Press '${LIGHTCYAN}q${YELLOW}' to exit. "
						echo -e ${YELLOW}"You can use arrow keys and page up/down to navigate the page."
						echo -e ${YELLOW}"This will show ALL active processes on all terminals/users."
						echo -e ${YELLOW}"Press any button to continue, or wait..."
						read -t 5 -n 1
						
						#Showing user stuff.
						echo -e -n ${GREEN}
						cat /etc/passwd	| less
						echo -e -n ${NC}
						clear
						
						#Informing user what happened!
						echo -e ${GREEN}"User stuff shown!"${NC}
						;;
					3)
						clear
						#Asking user what directory they want to have listed
						#Change CWD
						#Prompt for working directory.
						echo -e ${LIGHTPURPLE}"Please specify the ${LIGHTCYAN}working directory${LIGHTPURPLE}. All actions following will be relative to this."
						echo -e ${LIGHTPURPLE}"This expects the format to be: ${LIGHTCYAN}/XXX/XXX/XXX/${NC}"
						echo -e ${YELLOW}"(The directory will be created if it does not exist.)"
						echo -e -n ${LIGHTCYAN}
						read givenDirectory
						
						
						#Defaulting colour.
						echo -e -n ${NC}
						#Clearing screen.
						clear
						
						#DIRECTORY VALIDATION
						
						#Checking if it is a forward slash (/) - Continuing if it is, adding it if there is not one.
						if [[ $givenDirectory = */ ]]
						then
							#DO NOTHING. MIGHT BE VALID.
							clear
						else
							#Adding the slash.
							givenDirectory="${givenDirectory}/"
						fi
						
						#Ensuring the directory is valid. Creating it if it does not exist.
						if [ -d $givenDirectory ]
						then
							#Changing to directory.
							cd $givenDirectory
						else 
							#Telling user that the directory was not found, but is going to be created.
							echo -e ${RED}"Directory not found. Creating directory..."
							#Creating directory.
							mkdir -p $givenDirectory
							#Announcing that the directory is created.
							echo -e ${GREEN}$givenDirectory "created!"
							
							#Changing to directory.
							cd $givenDirectory
						fi
						
						echo -e -n ${WHITE}
						ls -la
						echo -e -n ${LIGHTPURPLE}
						read -n 1 -p "Press any key to continue..."
						echo -e -n ${NC}
						clear
						;;
					88)
						#Exit the User Operations submenu.
						clear
						break
						;;
					99)
						#Shutdown
						echo -e ${GREEN}"Powering off!"${NC}
						poweroff
						;;
				esac
			done
			;;
		#Process Menu Submenu
		4)
			while true;
			do
				#Displaying user operation submenu
				echo -e ${LIGHTPURPLE}"Please select a ${LIGHTCYAN}numeric option${LIGHTPURPLE} within the Process Menu submenu:"
				echo -e ${LIGHTCYAN}"1${NC}. Start a command with a defined nice level"
				echo -e ${LIGHTCYAN}"2${NC}. Change nice level of application"
				echo -e ${LIGHTCYAN}"3${NC}. View Processes"
				echo -e ${LIGHTCYAN}"4${NC}. Kill Processes"
				echo -e ${LIGHTCYAN}"5${NC}. Bring up Top"
				echo -e ${LIGHTCYAN}"6${NC}. Move a process to the background${RED} - This shit don't work. :("
				echo -e ${LIGHTCYAN}"88${NC}. Return to Main Menu"
				echo -e ${LIGHTCYAN}"99${NC}. Shut down"
				echo -e -n ${LIGHTCYAN}
				read processMenuSubmenu
				echo -e -n ${NC}
				
				#Clearing old shit.
				clear
				
				case $processMenuSubmenu in
					#Running command with nice.
					1)
						#Asking user what command they want to run.
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}command${LIGHTPURPLE} would you like to run?"
						echo -e -n ${LIGHTCYAN}
						read commandToRun
						
						#Asking user what niceness level they want the process to have.
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}niceness${LIGHTPURPLE} would you like this process to have?"
						echo -e ${YELLOW}"The lowest is -20. The highest can be 19. Default will be 10 (if nothing is provided)."
						echo -e -n ${LIGHTCYAN}
						read nicenessLevel
						
						#Checking if the niceness was provided.
						if [ ! -z $nicenessLevel ]
						then
							#Checking that only numbers were given (and a hyphen), and that the number is between -20 and 19.
							if [[ $nicenessLevel =~ ^[\-0-9]+$ ]] && [[ $nicenessLevel -lt 19 ]] && [[ $nicenessLevel -gt -20 ]]
							then
								clear
								#Setting niceness to given niceness level.
								nice -n $nicenessLevel $commandToRun &
								
								#Informing user that the command ran and what niceness level it was given.
								echo -e ${GREEN}"Niceness level:" ${LIGHTCYAN}$nicenessLevel ${GREEN}"given to command:" ${LIGHTCYAN}$commandToRun${NC}
							else
								clear
								
								#Invalid input provided.
								echo -e ${RED}"Invalid input provided for niceness level:" ${LIGHTCYAN}$nicenessLevel
								echo -e ${RED}"Please input a number between -20 and 19"
							fi
						else
							clear
							
							#No input given for niceness level.
							#Running nice with default 10 niceness.
							nicenessLevel=10
							nice -n $nicenessLevel $commandToRun &
							
							#Informing user that the command ran and what niceness level it was given.
							echo -e ${GREEN}"Niceness level:" ${LIGHTCYAN}$nicenessLevel ${GREEN}"given to command:" ${LIGHTCYAN}$commandToRun${NC}
						fi
						;;
					#Renice an existing process.
					2)
						#Asking user if they would like to view processes.
						echo -e ${LIGHTPURPLE}"Would you like to view all currently running processes (this will show the PID and current niceness level) ${LIGHTCYAN}Y/N${NC}"
						echo -e ${YELLOW}"Press 'q' to exit."
						echo -e -n ${LIGHTCYAN}
						read yesNoAnswer
						echo -e -n ${NC}
							
							#Reading the answer with the first letter of the input.
							case ${yesNoAnswer:0:1} in
								'Y')
									#Showing processes.
									echo -e -n ${GREEN}
									ps -e -f -l | less
									echo -e -n ${NC}
									;;
								'y')
									#Showing processes.
									echo -e -n ${GREEN}
									ps -e -f -l | less
									echo -e -n ${NC}
									;;
								'N')
									#Do nothing
									clear
									;;
								'n')
									#Do nothing
									clear
									;;
								*)
									clear
									#Catch all, no "yes" or "no" given.
									echo -e ${RED}"Invalid option:" ${LIGHTCYAN}$yesNoAnswer
									echo -e ${RED}"No changes made. Please try again."${NC}
									;;
							esac
						
						#Asking what process to renice.
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}process${LIGHTPURPLE} would you like to renice? Please provide the ${LIGHTCYAN}PID${LIGHTPURPLE}:"
						echo -e -n ${LIGHTCYAN}
						read processToRenice
						echo -e -n ${NC}
						
						#Checking if an answer was provided.
						if [ ! -z $processToRenice ]
						then
							#Asking user what the new niceness level should be.
							echo -e ${LIGHTPURPLE}"What would you like process" ${LIGHTCYAN}$processToRenice${LIGHTPURPLE} "new niceness to be?"
							read nicenessLevel
							#Verifying an answer was given.
							if [ ! -z $processToRenice ]
							then
								if [[ $nicenessLevel =~ ^[\-0-9]+$ ]] && [[ $nicenessLevel -lt 19 ]] && [[ $nicenessLevel -gt -20 ]]
								then
									clear
									
									echo -e -n ${WHITE}
									#Renicing the process with given niceness level.
									renice $nicenessLevel -p $processToRenice
									#Command will inform user.
									echo -e ${GREEN}"Reniced PID:" ${LIGHTCYAN}$processToRenice${GREEN} "to niceness level:" ${LIGHTCYAN}$nicenessLevel${GREEN}
								else
									clear
									#Invalid input provided.
									echo -e ${RED}"Invalid input provided for niceness level:" ${LIGHTCYAN}$nicenessLevel
									echo -e ${RED}"Please input a number between -20 and 19"
								fi
							else
								clear
								#No answer provided.
								echo -e ${RED}"No niceness level provided. Please try again."
							fi
						else
							clear
							#Informing user that no process ID was provided.
							echo -e ${RED}"No process ID given."
							echo -e ${RED}"No changes made. Please try again."
						fi
						
						;;
					3)
						#Informing user and adding a 7 second timer.
						echo -e ${YELLOW}"All running process will be shown soon. Press '${LIGHTCYAN}q${YELLOW}' to exit. "
						echo -e ${YELLOW}"You can use arrow keys and page up/down to navigate the page."
						echo -e ${YELLOW}"This will show ALL active processes on all terminals/users."
						echo -e -n ${NC}
						read -t 7
						
						echo -e -n ${GREEN}
						#Showing processes
						ps -e -f -l | less
						
						#clearing processes
						clear
						;;
					4)						
						#Asking user if they want to view process.
						echo -e ${LIGHTPURPLE}"Would you like to view running processes first?"
						echo -e ${YELLOW}"(Press enter with a blank prompt to skip the viewing process)"
						echo -e -n ${LIGHTCYAN}
						read processViewResponse
						echo -e -n ${NC}
						
						clear
						
						#If the response is NOT empty
						if [ ! -z $processViewResponse ]
						then
							#Informing user and adding a 5 second timer.
							echo -e ${YELLOW}"All running process will be shown soon. Press '${LIGHTCYAN}q${YELLOW}' to exit. "
							echo -e ${YELLOW}"You can use arrow keys and page up/down to navigate the page."
							read -t 5
							#Showing processes
							echo -e -n ${WHITE}
							ps -l -f | less
							echo -e -n ${NC}
							
						else
							#Do nothing.
							clear
						fi
						
						
						#Asking for PID of process to murder.
						echo -e ${LIGHTPURPLE}"What ${LIGHTCYAN}PID${LIGHTPURPLE} would you like to be ${RED}murdered${LIGHTPURPLE}?"
						echo -e -n ${LIGHTCYAN}
						read givenPID
						echo -e -n ${NC}
						
						clear
						
						#Killing process.
						echo -e -n ${WHITE}
						kill -s 9 $givenPID
						echo -e -n ${NC}
						
						#Telling user it was murdered
						echo -e ${GREEN}"You have ${RED}murdered${GREEN} poor process (PID):" ${LIGHTCYAN}$givenPID${NC}
						;;
					5)
						#Informing user and adding a 3 second timer.
						echo -e ${YELLOW}"The top command will begin running soon. Press 'q' to exit."
						read -t 3
						
						echo -e -n ${GREEN}
						#Running the top command.
						top -d 1
						
						echo -e -n ${NC}
						#Clearing top command stuff.
						clear
						;;
					6)
						#Showing current jobs.
						echo "Would you like to view current jobs first?"
						echo "(Press enter with a blank prompt to skip the viewing process)"
						read jobViewResponse
						
						clear
						
						#If the response is NOT empty
						if [ ! -z $jobViewResponse ]
						then
							#Showing jobs.
							echo "All running process will be shown soon. Press 'q' to exit. "
							echo "You can use arrow keys and page up/down to navigate the page."
							echo "Note: The PGID number is the job number. Use this to move the job to the background."
							read -t 5
							#*HELP*
							jobs -l | less
						else
							#Do nothing.
							clear
						fi
						
						#Asking user for job ID
						echo "What job ID would you like moved to the background?"
						read givenJobID
						
						#Ensuring jobid is actually numbers.
						if [[ $givenJobID =~ ^[0-9] ]]
						then
							#Verifying job is running/existant.
							tempVariable=""
							#*HELP*
							tempVariable=$(jobs -l "\["$givenJobID"\]")
							echo $tempVariable
							
							#Checking if the job was found in the jobs -l output
							if [[ ! -z $tempVariable ]]
							then
								#Moving task to background. Should be valid.
								bg $givenJobID
							else
								#Telling user that the job doesn't seem real.
								echo "Job ID not found:" $givenJobID
								echo "No action taken."
							fi
						else
							clear
							#Something other than numbers provided.
							echo "Error: Numbers expected. Please try again."
						fi
						
						;;
					88)
						break
						;;
					99)
						#Shutdown
						echo -e ${GREEN}"Powering off!"${NC}
						poweroff
						;;
				esac
			done
			;;
		#Fun Menu Submenu
		5)
			while true;
			do
				#Showing fun menu options.
				echo -e ${LIGHTPURPLE}"Choose a ${LIGHTCYAN}number${LIGHTPURPLE}, idiot."
				echo -e ${LIGHTCYAN}"1${NC}. Fizzbuzz"
				echo -e ${WHITE}"String Related Problems:"
				echo -e ${LIGHTCYAN}"\t2${NC}. Reverse a given string"
				echo -e ${LIGHTCYAN}"\t3${NC}. Check if a string is a palindrome"
				echo -e ${LIGHTCYAN}"\t5${NC}. Find the first instance of a repeated character"
				echo -e ${LIGHTCYAN}"\t6${NC}. Find the first instance of a non-repeated character"
				echo -e ${LIGHTCYAN}"88${NC}. Exit"
				echo -e ${LIGHTCYAN}"99${NC}. Shut Down"
				echo -e -n ${LIGHTCYAN}
				read funMenuSubmenu
				echo -e -n ${NC}
				
				clear
				
				case $funMenuSubmenu in
					#Fizzbuzz
					1)
						echo -e ${WHITE}"Prompt: Write a program that prints the numbers from 1 to 100 ${NC}(This can be chosen in my script)${WHITE}. But for multiples of three print “Fizz” instead of the number and for the multiples of five print “Buzz”. For numbers which are multiples of both three and five print “FizzBuzz”."
						echo -e ${LIGHTPURPLE}"How high should the game of FizzBuzz go?"
						echo -e ${YELLOW}"Provide a max number."
						echo -e -n ${LIGHTCYAN}
						read fizzbuzzNumberCap
						
						#Init the counters.
						counter=0
						fizzCounter=0
						buzzCounter=0
						fizzBuzzCounter=0
						neitherCounter=0
						
						clear
						#Verifying input value is a number.
						if [[ $fizzbuzzNumberCap =~ ^[0-9] ]]
						then
							#PIPE TO LESS
							(
								#While counter is less than defined cap.
								while [ $counter -lt $fizzbuzzNumberCap ] || [ $counter -eq $fizzbuzzNumberCap ];
								do
									if (( $counter % 3 == 0 )) && (( $counter % 5 == 0 ))
									then
										#FizzBuzz!
										echo -e ${RED}"Fizz${BLUE}Buzz"
										let fizzBuzzCounter+=1
									elif (( $counter % 3 == 0 ))
									then
										#Fizz
										echo -e ${RED}"Fizz"
										let fizzCounter+=1
									elif (( $counter % 5 == 0 ))
									then
										#Buzz
										echo -e ${BLUE}"Buzz"
										let buzzCounter+=1
									else
										#Fizz/Buzz not found.
										echo -e ${WHITE}$counter
										let neitherCounter+=1
									fi
									
									echo -e -n ${NC}
									
									let counter+=1
								done
								#Showing stats.
								echo -e ${NC}"Fizz Count:" ${RED}$fizzCounter
								echo -e ${NC}"Buzz Count:" ${BLUE}$buzzCounter
								echo -e ${NC}"FizzBuzz Count:" ${GREEN}$fizzBuzzCounter
								echo -e ${NC}"Boring Count:" ${WHITE}$neitherCounter
								echo -e ${YELLOW}"Press '${LIGHTCYAN}q${YELLOW}' to exit..."
							) | less -r
							
							clear
						else
							clear
							echo -e ${RED}"Invalid input:" ${LIGHTCYAN}$fizzbuzzNumberCap
							echo -e ${RED}"Please try again. (Try to input actual numbers next time. Stupid)"
						fi
						;;
					#Reverse a given string.
					2)
						#Initializing String Variable.
						normalString=""
						#Asking user for the string.
						echo -e ${LIGHTPURPLE}"Provide a ${LIGHTCYAN}string${LIGHTPURPLE} that you want reversed:"
						echo -e -n ${LIGHTCYAN}
						read normalString
						echo -e ${NC}
						
						clear
						
						#Checking that a string was provided.
						if [ ! -z $normalString ]
						then
							#Reversing string!
							reversedString=$(echo $normalString | rev )
							#Showing original string.
							echo -e ${GREEN}"You provided:" ${LIGHTCYAN}$normalString${NC}
							#Showing original string.
							echo -e ${GREEN}"Reversed:" ${LIGHTCYAN}$reversedString${NC}
						else
							#Telling user that a string was not given.
							echo -e ${RED}"No input provided. Please try again."
						fi
						;;
					#Checking if a string is a palindrome
					3)
						#Initializing String Variable.
						normalString=""
						#Asking user for the string.
						echo -e ${LIGHTPURPLE}"Provide a ${LIGHTCYAN}string${LIGHTPURPLE}. this will tell you if it is a palindrome or not."
						echo -e ${YELLOW}"(A palindrome is a word that is the same forward, and backwards. Example: \"racecar\")"
						echo -e -n ${LIGHTCYAN}
						read normalString
						echo -e ${NC}
						
						clear
						
						#Checking that a string was provided.
						if [ ! -z $normalString ]
						then
							
							
							#Holding lowercase version of normal string.
							tempString="${normalString,,}"
							#Reversing the lowercase version of the string!
							reversedString=$(echo $tempString | rev )
							
							#Comparing the two lowercase versions.
							if [ $tempString == $reversedString ]
							then
								#Strings match. It's a palindrome.
								echo -e ${LIGHTCYAN}$normalString${GREEN}" is a palindrome!"${NC}
							else
								#Strings don't match. Not a palindrome.
								echo -e ${LIGHTCYAN}$normalString${RED}" is NOT a palindrome."${NC}
							fi
							
						else
							#Telling user that a string was not given.
							echo -e ${RED}"No input provided. Please try again."${NC}
						fi
						;;
					#Find the first instance of a repeated character
					5)
						#Initializing String Variable.
						normalString=""
						#Asking user for the string.
						echo -e ${LIGHTPURPLE}"Provide a ${LIGHTCYAN}string${LIGHTPURPLE}. This will find the first set of repeating characters."
						echo -e ${YELLOW}"(This is case-sensitive and will show only TRUELY UNIQUE characters)"
						echo -e -n ${LIGHTCYAN}
						read normalString
						echo -e ${NC}
						
						clear
						
						#Checking that a string was provided.
						if [ ! -z $normalString ]
						then
							#Initializing character counter. This keeps track of the position in the string.
							characterCounter=1
							
							#Initializing past character for the first char.
							pastCharacter=$(expr substr "$normalString" 1 1)
							
							#Going through each character. Starting with 1.
							while [[ $characterCounter -lt ${#normalString} ]] || [[ $characterCounter -eq ${#normalString} ]];
							do
								#Grabbing current character to look at.
								currentCharacter=$(expr substr "$normalString" $characterCounter 1)
								
								#Checking if a repeated character is found.
								if [[ "$currentCharacter" = "$pastCharacter" ]] && [[ $characterCounter -eq 1 ]]
								then
									#Do nothing. First character will always match.
									echo -n ""
								elif [[ "$currentCharacter" = "$pastCharacter" ]]
								then
									#Repeating characters found.
									repeatingFound=1
									break	
								else
									#non-repeating characters found.
									#Do nothing.
									echo -n ""
								fi
								
								#Setting current character as new old character.
								pastCharacter=$currentCharacter
								#Adding to character.
								let "characterCounter++"
							done
							
							#Providing results. Check if a non-repeating character was found.
							if [[ $repeatingFound = 1 ]]
							then
								echo -e ${WHITE}"Provided string:" ${LIGHTCYAN}$normalString
								echo -e ${WHITE}"First instance of repeated character:" ${GREEN}$pastCharacter${WHITE} "at position:" ${GREEN}$(($characterCounter-1))${WHITE}
							else
								#No non-repeating characters found.
								echo -e ${RED}"Ain't shit found, dumby."
							fi
							
						else
							#Telling user that a string was not given.
							echo -e ${RED}"No input provided. Please try again."${NC}
						fi
						
						;;
					#Find the first instance of a non-repeated character
					6)
						#Initializing String Variable.
						normalString=""
						#Asking user for the string.
						echo -e ${LIGHTPURPLE}"Provide a ${LIGHTCYAN}string${LIGHTPURPLE}. This will find the first non-repeating character."
						echo -e ${YELLOW}"(This is case-sensitive and will show only TRUELY UNIQUE characters)"
						echo -e -n ${LIGHTCYAN}
						read normalString
						echo -e ${NC}
						
						clear
						
						#Checking that a string was provided.
						if [ ! -z $normalString ]
						then
							#Initializing character counter. This keeps track of the position in the string.
							characterCounter=1
							
							#Initializing past character for the first char.
							pastCharacter=$(expr substr "$normalString" 1 1)
							
							#Going through each character. Starting with 1.
							while [[ $characterCounter -lt ${#normalString} ]] || [[ $characterCounter -eq ${#normalString} ]];
							do
								#Grabbing current character to look at.
								currentCharacter=$(expr substr "$normalString" $characterCounter 1)
								
								#Checking if a repeated character is found.
								if [[ "$currentCharacter" = "$pastCharacter" ]] || [[ $characterCounter -eq 1 ]]
								then
									#Repeating characters found.
									#Do nothing.
									echo -n ""
								else
									#Non-Repeating characters found.
									nonRepeatingFound=1
									break
								fi
								
								#Setting current character as new old character.
								pastCharacter=$currentCharacter
								#Adding to character.
								let "characterCounter++"
							done
							
							#Providing results. Check if a non-repeating character was found.
							if [[ $nonRepeatingFound = 1 ]]
							then
								echo -e ${WHITE}"Provided string:" ${LIGHTCYAN}$normalString
								echo -e ${WHITE}"Last repeating character is:" ${GREEN}$pastCharacter${WHITE} "at position:" ${GREEN}$(($characterCounter-1))${WHITE}
								echo -e ${WHITE}"New character is:" ${GREEN}$currentCharacter${WHITE} "at position:" ${GREEN}$characterCounter${NC}
							else
								#No non-repeating characters found.
								echo -e ${RED}"Ain't shit found, dumby."
							fi
							
						else
							#Telling user that a string was not given.
							echo -e ${RED}"No input provided. Please try again."${NC}
						fi
						
						;;
					88)
						#Exit the entire script.
						exit
						;;
					#Power off
					99)
						#Shutdown
						echo -e ${GREEN}"Powering off!"${NC}
						poweroff
						;;
				esac
			done
			;;
		#Exit
		88)
			#Exit the entire script.
			exit
			;;
		#Power off
		99)
			#Shutdown
			echo -e ${GREEN}"Powering off!"${NC}
			poweroff
			;;
		#Colour coding and disable/enable.
		3030)
			while true;
			do
				#Showing current colours:
				echo -e ${WHITE}"Colors set to:" ${WHITE}$colorsOn${NC}
				#Showing color code.
				echo -e ${RED}"Shows errors."
				echo -e ${YELLOW}"Shows warnings or additional information."
				echo -e ${GREEN}"Shows good things."
				echo -e ${LIGHTCYAN}"This typically indicates what information the script is looking for."
				echo -e ${LIGHTPURPLE}"Shows prompts. This is typically asking the user to do something."
				echo ""
				#Asking for option.
				echo -e ${LIGHTPURPLE}"Would you like colors to be ${LIGHTCYAN}enabled${LIGHTPURPLE}, or ${LIGHTCYAN}disabled${LIGHTPURPLE}?"
				echo -e ${WHITE}"Enable = ${LIGHTCYAN}1${WHITE} | Disable = ${LIGHTCYAN}0${WHITE} | ${LIGHTCYAN}Pressing enter${WHITE} will exit."
				echo -e ${WHITE}"This option is currently set to:" $colorsOn
				
				echo -e -n ${LIGHTCYAN}
				read -n 1 yesNoAnswer
				echo -e -n ${NC}
				
				#Reading the answer with the first letter of the input.
				case ${yesNoAnswer:0:1} in
					0)
						#Changing colors option.
						colorsOn=0
						
						#Clearing screen.
						clear
						
						#Setting colours to black.
						BLACK="\e[0m"
						RED="\e[0m"
						GREEN="\e[0m"
						BROWNORANGE="\e[0m"
						BLUE="\e[0m"
						PURPLE="\e[0m"
						CYAN="\e[0m"
						LIGHTGRAY="\e[0m"
						DARKGRAY="\e[0m"
						LIGHTRED="\e[0m"
						LIGHTGREEN="\e[0m"
						YELLOW="\e[0m"
						LIGHTBLUE="\e[0m"
						LIGHTPURPLE="\e[0m"
						LIGHTCYAN="\e[0m"
						WHITE="\e[0m"
						;;
					1)
						#Changing colors option.
						colorsOn=1
						
						#Clearing screen.
						clear
						
						#Setting colours to black.
						BLACK="\e[0;30m"
						RED="\e[0;31m"
						GREEN="\e[0;32m"
						BROWNORANGE="\e[0;33m"
						BLUE="\e[0;34m"
						PURPLE="\e[0;35m"
						CYAN="\e[0;36m"
						LIGHTGRAY="\e[0;37m"
						DARKGRAY="\e[0;90m"
						LIGHTRED="\e[0;91m"
						LIGHTGREEN="\e[0;92m"
						YELLOW="\e[0;93m"
						LIGHTBLUE="\e[0;94m"
						LIGHTPURPLE="\e[0;95m"
						LIGHTCYAN="\e[0;96m"
						WHITE="\e[0;97m"
						;;
					"")
						clear
						echo -e ${WHITE}"Colors set to:" ${LIGHTCYAN}$colorsOn${NC}
						break
						;;
					*)
						clear
						;;
				esac
			done
			;;
	esac
done











