<?php

namespace ayd1ndemirci\EconomyAPI\command;

use ayd1ndemirci\EconomyAPI\Economy;
use pocketmine\command\Command;
use pocketmine\command\CommandSender;
use pocketmine\player\Player;
use pocketmine\utils\TextFormat;

class MyMoneyCommand extends Command
{
    public Economy $economy;

    public function __construct(Economy $economy)
    {
        $this->economy = $economy;

        parent::__construct(
            "mymoney",
            "See your money"
        );

        $this->setPermission("pocketmine.group.user");
    }

    public function execute(CommandSender $sender, string $commandLabel, array $args)
    {
        if (!$sender instanceof Player) return;

        $sender->sendMessage(sprintf(TextFormat::GREEN . "Your Money: " . TextFormat::WHITE . "$%s", $this->economy->getManager()->getPlayerMoney($sender->getName())));
    }
}