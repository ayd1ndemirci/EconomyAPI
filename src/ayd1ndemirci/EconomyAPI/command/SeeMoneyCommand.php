<?php

namespace ayd1ndemirci\EconomyAPI\command;

use ayd1ndemirci\EconomyAPI\Economy;
use pocketmine\command\Command;
use pocketmine\command\CommandSender;
use pocketmine\player\Player;
use pocketmine\Server;
use pocketmine\utils\TextFormat;

class SeeMoneyCommand extends Command
{

    public Economy $economy;

    public function __construct(Economy $economy)
    {
        $this->economy = $economy;
        parent::__construct(
            "seemoney",
            "See money to player"
        );
        $this->setPermission("pocketmine.group.user");
    }

    public function execute(CommandSender $sender, string $commandLabel, array $args)
    {
        if (!$sender instanceof Player) return;

        if (empty($args[0])) {
            $sender->sendMessage(TextFormat::RED . "/seemoney player");
            return;
        }
        $player = Server::getInstance()->getPlayerByPrefix($args[0]);
        if ($player instanceof Player) {
            $sender->sendMessage(sprintf(TextFormat::GREEN .  "%s's money is %s", $player->getName(), $this->economy->getManager()->getPlayerMoney($player->getName())));
        } else $sender->sendMessage(sprintf(TextFormat::RED . "The player named %s could not be found", $args[0]));
    }
}