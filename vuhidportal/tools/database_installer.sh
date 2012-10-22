#!/bin/bash

############################################
##           WARNING!  WARNING!           ##
##                                        ##
## Don't edit this script on Windows OS   ##
## Or use a software which allows you to  ##
## write in UNIX type                     ##
############################################
## Written by Long Phan                   ##
## License: GNU GPL                       ##
## Based on L2JDP script                  ##
############################################

# Catch kill signals
trap finish 1 2 15

# Configure the database access
configure()
{
	echo "#################################################"
	echo "#               Configuration area              #"
	echo "#         Please answer to the questions        #"
	echo "#################################################"
	MYSQLDUMPPATH=`which mysqldump 2>/dev/null`
	MYSQLPATH=`which mysql 2>/dev/null`
	if [ $? -ne 0 ]; then
		echo "Unable to find MySQL binaries on your PATH"
		while :
		do
			echo -ne "\nPlease enter MySQL binaries directory (no trailing slash): "
			read MYSQLBINPATH
			if [ -e "$MYSQLBINPATH" ] && [ -d "$MYSQLBINPATH" ] && [ -e "$MYSQLBINPATH/mysqldump" ] && [ -e "$MYSQLBINPATH/mysql" ]; then
				MYSQLDUMPPATH="$MYSQLBINPATH/mysqldump"
				MYSQLPATH="$MYSQLBINPATH/mysql"
				break
			else
				echo "Invalid data. Please verify and try again."
				exit 1
			fi
		done
	fi

	# LoginServer
	echo -ne "\nPlease enter MySQL VUHID Portal hostname (default localhost): "
	read DBHOST
	if [ -z "$DBHOST" ]; then
		DBHOST="localhost"
	fi
	echo -ne "\nPlease enter MySQL VUHID Portal database name (default vuhid-portal): "
	read DBNAME
	if [ -z "$DBNAME" ]; then
		DBNAME="vuhid-portal"
	fi
	echo -ne "\nPlease enter MySQL VUHID Portal user (default root): "
	read DBUSER
	if [ -z "$DBUSER" ]; then
		DBUSER="root"
	fi
	echo -ne "\nPlease enter MySQL VUHID Portal $LSUSER's password (won't be displayed) :"
	stty -echo
	read DBPASS
	stty echo
	echo ""
	if [ -z "$DBPASS" ]; then
		echo "Please avoid empty password else you will have a security problem."
	fi
}

# Actions which can be performed
action_type()
{
	echo "#################################################"
	echo "#           Database Installer Script           #"
	echo "#################################################"
	echo ""
	echo "What do you want to do?"
	echo "Database backup           [b] (make a backup of the existing tables)"
	echo "Full installation         [f] (for first installation, this will erase all the existing tables)"
	echo "Update non critical data  [u] (Only erase and reinsert tables without players' data)"
	echo "Insert one table          [t] (Only insert one table in your database)"
	echo "Quit this script          [q]"
	echo -ne "Choice: "
	read ACTION_CHOICE
	case "$ACTION_CHOICE" in
		"b"|"B") backup_db; finish;;
		"r"|"R") insert_backup; finish;;
		"f"|"F") full_install; finish;;
		"u"|"U") update_db noncritical; finish;;
		"t"|"T") table_insert;;
		"q"|"Q") finish;;
		*)       action_type;;
	esac
}

# Make a backup of the LS and GS database
backup_db()
{
	echo "#################################################"
	echo "#                Database Backup                #"
	echo "#################################################"
	echo ""
	echo "VUHID Portal backup"
	$MYSQLDUMPPATH --add-drop-table -h $DBHOST -u $DBUSER --password=$DBPASS $DBNAME > vuhid-portal_backup.sql
}

# Full installation (erase and insert all tables)
full_install()
{
	echo "#################################################"
	echo "#          Full Database Installation           #"
	echo "#################################################"
	echo ""
	echo "VUHID Portal database"
	$MYDB < ../sql/vuhid-portal.sql &> /dev/null
	$MYDBD < ../sql/transactions.sql &> /dev/null
}

# End of the script
finish()
{
	echo ""
	echo "Script execution finished."
	exit 0
}

# Clear console
clear

# Call configure function
configure

# Open MySQL connections
MYDBD="$MYSQLPATH -h $DBHOST -u $DBUSER --password=$DBPASS -D $DBNAME"
MYDB="$MYSQLPATH -h $DBHOST -u $DBUSER --password=$DBPASS"

# Ask action to do
action_type