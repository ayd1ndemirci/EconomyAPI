<?php

namespace ayd1ndemirci\EconomyAPI\command;

use ayd1ndemirci\EconomyAPI\Economy;
use pocketmine\command\Command;
use pocketmine\command\CommandSender;
use pocketmine\player\Player;
use pocketmine\Server;
use pocketmine\utils\TextFormat;
use SOFe\AwaitGenerator\Await;

class AddMoneyCommand extends Command
{
    public Economy $economy;

    public function __construct(Economy $economy)
    {
        $this->economy = $economy;
        parent::__construct(
            "addmoney",
            "Add player money"
        );
        $this->setPermission("pocketmine.group.user");
    }

    public function execute(CommandSender $sender, string $commandLabel, array $args): void
    {
        if (!$sender instanceof Player) return;

        if (!isset($args[0], $args[1])) {
            $sender->sendMessage(TextFormat::RED . "/addmoney player money");
            return;
        }

        $player = $args[0];
        $money = $args[1];

        if (!is_numeric($money)) {
            $sender->sendMessage(TextFormat::RED . "Enter numeric value");
            return;
        }

        if (Server::getInstance()->getPlayerByPrefix($player) instanceof Player) {
            $message = sprintf(TextFormat::GREEN . "%s money sent to player %s", $money, Server::getInstance()->getPlayerByPrefix($player)->getName());
            $sender->sendMessage($message);
            $this->economy->getManager()->addPlayerMoney(Server::getInstance()->getPlayerByPrefix($player)->getName(), $money);
        } else {
            Await::f2c(function () use($player) {
                $rows = (array) yield from $this->economy->getDatabase()->getPlayerMoney($player);
                if(empty($rows)) $this->economy->getDatabase()->addPlayer($player);
            });
            $sender->sendMessage(sprintf(TextFormat::GREEN . "%s money sent to player %s", $money, $player));
            $this->economy->getManager()->addPlayerMoney($player, $money);
        }
    }
}