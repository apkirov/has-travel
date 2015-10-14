#!/usr/bin/perl
use strict;
die "You specify the dictinary file: \n\n perl language-check.pl res.dat\n\n" unless ($ARGV[0]);
open FR, $ARGV[0] or dir $! .' '. $ARGV[0];
my $lang = <FR>;
$lang =~ s/\s//gmsi;
my $word = <FR>;
close(FR);
$word =~ s/\s//gmsi;
my %letters;

while  ($lang =~ /(.)\^n/gs) {
	if ($word !~ s/(.)\1*(.*)/$2/gmsi) {
		print "no\n";
		exit(0);
	}
}

if ( length($word) ) {
	print "no\n";			

}
else {
	print "yes\n";			
}
